# WooCommerce Web Scraper

## Description
A Spring Boot application designed to scrape product data from WooCommerce-based websites. The application fetches product information through the WooCommerce REST API and saves it to CSV files with timestamps.

## Features
- Fetches products using WooCommerce Store API `/wp-json/wc/store/v1/products`
- Supports pagination
- Saves data in CSV format with timestamps
- Progress tracking with animated console output
- Price formatting for Polish currency (PLN)
- HTML stripping from descriptions
- Configurable through application.properties

## Prerequisites
- Java 17 or higher
- Maven
- Spring Boot 3.x

## Configuration
Edit `application.properties`:
```properties
woocommerce.base-url=https://your-store.com
```

## Project Structure
```
com.sevastijan.woocommercewebscrapper
├── config
│   ├── ScraperConfig.java
│   └── WooCommerceConfig.java
├── models
│   ├── Product.java
│   ├── Category.java
│   ├── Image.java
│   └── Prices.java
├── repositories
│   └── WooCommerceRepository.java
├── services
│   └── ScraperService.java
└── observers
    └── LoggingObserver.java
```

## Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
    </dependency>
    <dependency>
        <groupId>com.opencsv</groupId>
        <artifactId>opencsv</artifactId>
        <version>5.7.1</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

## Usage
1. Configure your WooCommerce store URL in `application.properties`
2. Run the application:
```bash
mvn spring-boot:run
```
3. CSV files will be generated in `src/main/resources/` with timestamps

## Output Format
The CSV file includes the following information:
- Product ID
- Name
- Description
- Prices (regular, sale)
- Categories
- Attributes
- Stock status
- Images URLs
- And more...

## Contributing
Feel free to submit issues and enhancement requests.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Authors
- Sevastijan

## Acknowledgments
- WooCommerce REST API
- Spring Boot Framework
- OpenCSV library
