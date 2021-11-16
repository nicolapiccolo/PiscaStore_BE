package it.unito.pisca.service;

import it.unito.pisca.entity.Category;
import it.unito.pisca.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public Category findCategory(long id) {
        Optional<Category> opt = categoryRepository.findById(id);

        if(opt.isPresent())
            return opt.get();
        else
            return null;
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(new Category(category.getName(),category.getDescription()));
    }
}
