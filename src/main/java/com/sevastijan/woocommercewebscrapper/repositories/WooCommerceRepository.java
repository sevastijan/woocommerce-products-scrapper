package com.sevastijan.woocommercewebscrapper.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.sevastijan.woocommercewebscrapper.config.WooCommerceConfig;
import com.sevastijan.woocommercewebscrapper.models.Category;
import com.sevastijan.woocommercewebscrapper.models.Product;
import com.sevastijan.woocommercewebscrapper.models.Term;
import com.sevastijan.woocommercewebscrapper.observers.ProductObserver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class WooCommerceRepository implements ProductRepository{
    private final List<ProductObserver> observers = new ArrayList<>();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final WooCommerceConfig config;
    private final ObjectMapper objectMapper;


    public WooCommerceRepository(WooCommerceConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("WooCommerceConfig cannot be null");
        }

        this.config = config;
        this.objectMapper = new ObjectMapper();
    }

    public void addObserver(ProductObserver observer) {
        observers.add(observer);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        int currentPage = 1;

        try {
            while (true) {
                String url = config.buildUrl(currentPage);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new IOException("Failed to fetch products. Status: " + response.statusCode());
                }

                List<Product> pageProducts = objectMapper.readValue(
                        response.body(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class)
                );

                if (pageProducts.isEmpty()) {
                    break;
                }

                allProducts.addAll(pageProducts);
                notifyObservers(pageProducts);
                currentPage++;
            }
        } catch (Exception e) {
            notifyError(e);
            throw new RuntimeException("Error fetching products", e);
        }

        return allProducts;
    }

    @Override
    public void saveToCSV(String filename, List<Product> products) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filenameWithTimestamp = String.format("%s_%s.csv", filename.replace(".csv", ""), timestamp);

        String resourcePath = "src/main/resources/";
        File directory = new File(resourcePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fullPath = resourcePath + filenameWithTimestamp;

        try (CSVWriter writer = new CSVWriter(new FileWriter(fullPath))) {
            writer.writeNext(new String[]{
                    "ID", "Name", "Slug", "Parent", "Type", "Variation", "Permalink", "SKU",
                    "Short Description", "Description", "On Sale", "Price", "Regular Price",
                    "Sale Price", "Currency", "Average Rating", "Review Count",
                    "Image URL", "Image Thumbnail", "Categories", "Attributes", "Is Purchasable",
                    "In Stock", "On Backorder", "Low Stock"
            });

            for (Product product : products) {
                String categories = product.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.joining(";"));

                String attributes = product.getAttributes().stream()
                        .map(attr -> attr.getName() + ": " +
                                attr.getTerms().stream()
                                        .map(Term::getName)
                                        .collect(Collectors.joining(", ")))
                        .collect(Collectors.joining(";"));

                String imageUrl = !product.getImages().isEmpty() ? product.getImages().get(0).getSrc() : "";
                String imageThumbnail = !product.getImages().isEmpty() ? product.getImages().get(0).getThumbnail() : "";

                writer.writeNext(new String[]{
                        product.getId().toString(),
                        product.getName(),
                        product.getSlug(),
                        product.getParent().toString(),
                        product.getType(),
                        product.getVariation(),
                        product.getPermalink(),
                        product.getSku(),
                        stripHtml(product.getShortDescription()),
                        stripHtml(product.getDescription()),
                        String.valueOf(product.isOnSale()),
                        formatPrice(product.getPrices().getPrice()),
                        formatPrice(product.getPrices().getRegularPrice()),
                        formatPrice(product.getPrices().getSalePrice()),
                        formatPrice(product.getPrices().getCurrencyCode()),
                        product.getAverageRating(),
                        String.valueOf(product.getReviewCount()),
                        imageUrl,
                        imageThumbnail,
                        categories,
                        attributes,
                        String.valueOf(product.isPurchasable()),
                        String.valueOf(product.isInStock()),
                        String.valueOf(product.isOnBackorder()),
                        product.getLowStockRemaining() != null ?
                                product.getLowStockRemaining().toString() : ""
                });
            }

            System.out.println("CSV FILE HAS BEEN SAVED: " + fullPath);
            System.out.println("FETCHED " + products.size() + " PRODUCTS");
            System.out.println("FETCH DATE: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        } catch (IOException e) {
            notifyError(e);
            throw new RuntimeException("Error saving to CSV", e);
        }

    }

    private void notifyObservers(List<Product> products) {
        observers.forEach(observer -> observer.onProductsFetched(products));
    }

    private void notifyError(Exception e) {
        observers.forEach(observer -> observer.onError(e));
    }

    private String stripHtml(String html) {
        if (html == null) return "";
        return html.replaceAll("\\<.*?\\>", "")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&bdquo;", "\"")
                .replaceAll("&rdquo;", "\"")
                .replaceAll("&#8211;", "-")
                .replaceAll("&amp;", "&")
                .replaceAll("\\s+", " ")
                .trim();
    }

    private String formatPrice(String priceInCents) {
        if (priceInCents == null || priceInCents.isEmpty()) {
            return "0,00";
        }

        try {
            double price = Double.parseDouble(priceInCents);
            price = price / 100;
            DecimalFormat df = new DecimalFormat("#,##0.00");
            df.setDecimalFormatSymbols(new DecimalFormatSymbols(new Locale("pl", "PL")));
            return df.format(price);
        } catch (NumberFormatException e) {
            return priceInCents;
        }
    }
}
