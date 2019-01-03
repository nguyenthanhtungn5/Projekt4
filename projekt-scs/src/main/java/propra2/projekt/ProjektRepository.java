package propra2.projekt;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProjektRepository extends CrudRepository<Projekt,Long> {
    List<Projekt> findAll();
}
