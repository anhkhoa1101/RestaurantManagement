import java.util.Locale;

public class MenuItem {
    private int itemId;
    private String name;
    private String description;
    private double price;
    private Category category;
    private boolean isAvailable;

    public MenuItem(int itemId, String name, String description, double price, Category category) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isAvailable = true;
    }

    public int getItemId() {
        return itemId;
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

    public Category getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price must not be negative.");
        this.price = price;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Item name must not be empty.");
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    //    public boolean matchesKeyword(String keyword) {
//        if (keyword == null || keyword.isBlank())
//            return true;
//        String kw = keyword.trim().toLowerCase();
//        return name.toLowerCase().contains(kw) || (description != null && description.toLowerCase().contains(kw));
//    }

    @Override
    public String toString() {
        return String.format("MenuItem{id=%d, name='%s', price=%.2f, available=%s, category='%s'}", itemId, name, price, isAvailable, category != null ? category.getName() : "N/A");
    }
}