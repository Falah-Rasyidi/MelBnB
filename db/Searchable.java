package db;
import java.util.List;

// A class implementing this interface can perform searching
public interface Searchable<T> {
    List<T> search(String keyword);
}