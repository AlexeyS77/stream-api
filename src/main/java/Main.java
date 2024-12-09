import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) {

        //наборы продуктов
        Set<Product> productsSet1 = Set.of(
                new Product(1L, "Toy Car", "Toys", new BigDecimal("9.99")),
                new Product(2L, "Doll", "Toys", new BigDecimal("14.99")),
                new Product(3L, "Children's Book", "Books", new BigDecimal("7.99")),
                new Product(5L, "Storybook", "Books", new BigDecimal("1.99")),
                new Product(6L, "Science Book", "Books", new BigDecimal("115.99"))
        );

        Set<Product> productsSet2 = Set.of(
                new Product(7L, "Action Camera", "Electronics", new BigDecimal("89.99")),
                new Product(8L, "Remote Control Car", "Toys", new BigDecimal("49.99")),
                new Product(9L, "Building Blocks", "Children's products", new BigDecimal("29.99")),
                new Product(10L, "Kids' Tablet", "Electronics", new BigDecimal("199.99")),
                new Product(11L, "Craft Kit", "Toys", new BigDecimal("24.99"))
        );

        Set<Product> productsSet3 = Set.of(
                new Product(12L, "Magic Set", "Toys", new BigDecimal("39.99")),
                new Product(13L, "Kids' Camera", "Electronics", new BigDecimal("59.99")),
                new Product(14L, "Board Puzzle", "Toys", new BigDecimal("25.99")),
                new Product(15L, "Adventure Storybook", "Books", new BigDecimal("110.99")),
                new Product(16L, "Coloring Book", "Books", new BigDecimal("5.99"))
        );

        Set<Product> productsSet4 = Set.of(
                new Product(17L, "Toy Train Set", "Toys", new BigDecimal("79.99")),
                new Product(18L, "Dinosaur Figurines", "Toys", new BigDecimal("39.99")),
                new Product(19L, "Kids' Cookbook", "Books", new BigDecimal("18.99")),
                new Product(20L, "Magic Markers Set", "Children's products", new BigDecimal("14.99")),
                new Product(21L, "Outdoor Sports Ball", "Sports Equipment", new BigDecimal("29.99"))
        );

        Set<Product> productsSet5 = Set.of(
                new Product(22L, "Animal Plush Toy", "Toys", new BigDecimal("34.99")),
                new Product(23L, "Kids' Art Easel", "Arts & Crafts", new BigDecimal("79.99")),
                new Product(24L, "Adventure Board Game", "Games", new BigDecimal("39.99")),
                new Product(25L, "Kids' Science Book Set", "Books", new BigDecimal("129.99")),
                new Product(26L, "Outdoor Explorer Kit", "Children's products", new BigDecimal("54.99"))
        );

        //заказы с наборами продуктов
        Order order1 = new Order(
                1L,
                LocalDate.of(2021, 1, 31),
                LocalDate.of(2021, 10, 5),
                "Shipped", productsSet1);
        Order order2 = new Order(
                2L,
                LocalDate.of(2021, 4, 25),
                LocalDate.of(2021, 10, 7),
                "Processing", productsSet2);
        Order order3 = new Order(
                3L,
                LocalDate.of(2021, 3, 15),
                LocalDate.of(2021, 10, 10),
                "Delivered", productsSet3);
        Order order4 = new Order(
                4L,
                LocalDate.of(2021, 11, 4),
                LocalDate.of(2021, 11, 12),
                "Pending", productsSet4);
        Order order5 = new Order(
                5L,
                LocalDate.of(2021, 12, 5),
                LocalDate.of(2021, 12, 15),
                "Cancelled", productsSet5);

        //список потребителей
        List<Customer> customers = List.of(
                new Customer(1L, "Alice", 1L, Set.of(order1, order2, order3, order4, order5)),
                new Customer(2L, "Bob", 2L, Set.of(order5, order4, order3, order2, order1)),
                new Customer(3L, "Charlie", 1L, Set.of(order1, order4, order3, order2, order5)),
                new Customer(4L, "David", 3L, Set.of(order1, order2, order3, order4, order5)),
                new Customer(5L, "Eve", 2L, Set.of(order5, order4, order1, order2, order3))
        );

        // Задание 1 (Получите список продуктов из категории "Books" с ценой более 100)
        List<Product> booksOver100 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .filter(product -> product.getPrice().compareTo(BigDecimal.valueOf(100)) > 0) // добавлен метод valueOf()
                .collect(Collectors.toList());
        // Задание 2 (Получите список заказов с продуктами из категории "Children's products")
        List<Order> ChildrenProductsOrderList = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getProducts().stream().anyMatch(product -> product.getCategory().equals("Children's products")))
                .collect(Collectors.toList());

        // Задание 3 (Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов)
        BigDecimal totalProductSumWithDiscount = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Toys"))
                .map(product -> product.getPrice().multiply(BigDecimal.ONE.subtract(new BigDecimal("0.10")))) // Применяем скидку 10%
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Суммируем

        // Задание 4 (Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021)
        List<Product> products = customers.stream()
                .filter(customer -> customer.getLevel() == 2L).flatMap(customer -> customer.getOrders().stream() // Получаем заказы каждого клиента
                        .filter(order -> {
                            LocalDate orderDate = order.getOrderDate(); // Получаем дату заказа
                            return orderDate.isAfter(LocalDate.of(2021, 2, 1)) // Заказ после 1 февраля 2021 (исправлено)
                                    && orderDate.isBefore(LocalDate.of(2021, 4, 1)); // Заказ до 1 апреля 2021 (исправлено)
                        })
                        .flatMap(order -> order.getProducts().stream())) // Извлекаем продукты из отфильтрованных заказов
                .collect(Collectors.toList()); // Собираем все продукты в список

        // Задание 5 (Получите топ 2 самые дешевые продукты из категории "Books")
        List<Product> cheapestTwoBooks = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .sorted(Comparator.comparing(Product::getPrice))
                .distinct()
                .limit(2)
                .collect(Collectors.toList());


        // Задание 6 (Получите 3 самых последних сделанных заказа)
        List<Order> threeLastOrders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

        // Задание 7 (Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов)
        List<Product> productsFromOrdersOnFifteenthMarch2021 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 15)))
                .peek(order -> System.out.println("ID заказа: " + order.getId()))
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());

        // Задание 8 (Рассчитайте общую сумму всех заказов, сделанных в феврале 2021)
        BigDecimal february2021OrderSum = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().getYear() == 2021 && order.getOrderDate().getMonthValue() == 2)
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, (bigDecimal, currentPrice) -> bigDecimal.add(currentPrice));

        // Задание 9 Рассчитайте средний платеж по заказам, сделанным 14-марта-2021.
        Double averagePayment14March2021 = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(order -> order.getOrderDate().isEqual(
                        LocalDate.of(2021, 3, 14)))
                .flatMap(order -> order.getProducts().stream())
                .mapToDouble(product -> product.getPrice().doubleValue())
                .average()
                .orElseThrow(() -> new IllegalStateException("Не удалось вычислить среднее значение цен продуктов на 14 марта 2021 года!"));

        // Задание 10 (Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех продуктов категории "Книги")

        Map<String, Double> booksOrderStatistic = new HashMap<>();
        List<Double> prices = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(product -> product.getCategory().equals("Books"))
                .map(product -> product.getPrice().doubleValue())
                .toList();
        double sum = prices.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
        booksOrderStatistic.put("Сумма", sum);
        double average = prices.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() ->
                        new IllegalStateException("Невозможно вычислить среднее значение: список цен пуст!"));
        booksOrderStatistic.put("Среднее", average);
        double max = prices.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Невозможно вычислить максимальную сумму заказа: список цен пуст!"));
        booksOrderStatistic.put("Максимум", max);
        double min = prices.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElseThrow(() ->
                        new IllegalStateException("Невозможно вычслить минимальную цену товара: список цен пуст!"));
        booksOrderStatistic.put("Минимум", min);
        double count = prices.stream().count();
        booksOrderStatistic.put("Количество", count);

        //Задание 11 (Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе)
        Map<Long, Integer> ordersCountMap = customers.stream().flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap((Order order) -> order.getId(), order -> order.getProducts().size()));

        // Задание 12 ( Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов)
        Map<Customer, List<Order>> ordersMap = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        customer -> List.copyOf(customer.getOrders())));

        // Задание 13 (Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа)
        Map<Order, Double> orderSumMap = customers.stream().flatMap(customer -> customer.getOrders().stream())
                .collect(Collectors.toMap(order -> order,  order -> order.getProducts().stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .doubleValue()
                ));
        // Задание 14 (Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории)
        Map<String, List<String>> categoryToProductNamesMap = customers.stream().flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .distinct()
                .collect(Collectors.groupingBy(
                        Product::getCategory, // Группируем по категории
                        Collectors.mapping(Product::getName, Collectors.toList()) // Собираем названия продуктов в список
                ));

        // Задание 15 (Получите Map<String, Product> → самый дорогой продукт по каждой категории)
        Map<String, Product> mostExpensiveProductsByCategory = customers.stream()
                    .flatMap(customer -> customer.getOrders().stream())
                    .flatMap(order -> order.getProducts().stream())
                    .distinct()
                    .collect(Collectors.groupingBy(Product::getCategory,
                            Collectors.collectingAndThen(
                                    Collectors.maxBy(Comparator.comparing(Product::getPrice)),
                                    productOptional -> productOptional.orElse(null))));


    }
}



