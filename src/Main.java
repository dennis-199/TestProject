import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Product {
    private String name;
    private String description;
    private double price;
    private Vendor vendor;

    public Product(String name, String description, double price, Vendor vendor) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Vendor getVendor() {
        return vendor;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price= ksh " + price +
                ", vendor=" + vendor.getUsername() +
                '}';
    }
}

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Customer extends User {
    public Customer(String username, String password) {
        super(username, password);
    }

    public void viewProducts(ProductCatalog catalog) {
        System.out.println("Available Products:");
        for (Product product : catalog.getProducts()) {
            System.out.println(product);
        }
    }

    public void placeOrder() {
        System.out.println("Placing an order...");
    }
}

class Vendor extends User {
    private ArrayList<Product> vendorProducts;

    public Vendor(String username, String password) {
        super(username, password);
        this.vendorProducts = new ArrayList<>();
    }

    public void addProduct(ProductCatalog catalog, Scanner scanner) {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Product newProduct = new Product(name, description, price, this);
        vendorProducts.add(newProduct);
        catalog.addProduct(newProduct);

        System.out.println("Product added successfully!");
    }

    public void viewVendorProducts() {
        System.out.println("Your Products:");
        for (Product product : vendorProducts) {
            System.out.println(product);
        }
    }

    public void viewOrders() {
        System.out.println("Viewing incoming orders...");
    }
}

class ProductCatalog {
    private ArrayList<Product> products;

    public ProductCatalog() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}

public class Main {
    private static HashMap<String, User> users = new HashMap<>();
    private static ProductCatalog productCatalog = new ProductCatalog();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\nWelcome to the JUAKALI System!");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        // Check if username already exists
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Are you a customer or a vendor? (c/v) ");
        char userType = scanner.nextLine().charAt(0);

        User user;
        if (userType == 'c') {
            user = new Customer(username, password);
        } else if (userType == 'v') {
            user = new Vendor(username, password);
        } else {
            System.out.println("Invalid user type. Registration failed.");
            return;
        }

        users.put(username, user);
        System.out.println("Registration successful!");
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");

            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                customerMenu(customer);
            } else if (user instanceof Vendor) {
                Vendor vendor = (Vendor) user;
                vendorMenu(vendor);
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Place Order");
            System.out.println("3. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    customer.viewProducts(productCatalog);
                    break;
                case 2:
                    customer.placeOrder();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void vendorMenu(Vendor vendor) {
        while (true) {
            System.out.println("\nVendor Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. View Your Products");
            System.out.println("3. View Orders");
            System.out.println("4. Logout");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    vendor.addProduct(productCatalog, scanner);
                    break;
                case 2:
                    vendor.viewVendorProducts();
                    break;
                case 3:
                    vendor.viewOrders();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}