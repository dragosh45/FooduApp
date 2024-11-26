package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.model.Category;
import com.tinderrestaurant.Tinder.Restaurants.repository.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(MockitoJUnitRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class CategoryDAOTest {
  @Mock private CategoryDAO categoryDAO;
  @Mock private CategoryRepository categoryRepository;

  @Before
  public void setup() {
    categoryRepository = Mockito.mock(CategoryRepository.class);
    // TODO: fix your tests
    //        categoryDAO = new CategoryDAO(categoryRepository);
  }

  @Test
  public void newCategoryTest() {
    Mockito.when(categoryDAO.save(any(Category.class))).thenReturn(new Category("Indian"));
    Category category = new Category("Indian");
    assertThat(categoryDAO.save(category)).isNotNull();
  }

  @Test
  public void newCategoryWithIdTest() {
    Mockito.when(categoryDAO.save(any(Category.class)))
        .then(
            new Answer<Category>() {
              @Override
              public Category answer(InvocationOnMock invocation) {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {

                  Category category = (Category) arguments[0];
                  category.setId(new Long(1));
                  return category;
                }
                return null;
              }
            });
    Category category = new Category("Indian");
    assertThat(categoryDAO.save(category)).isNotNull();
  }

  @Test
  public void deleteCategoryTest() {
    categoryDAO.delete(new Long(1));
    verify(categoryRepository).deleteById(any(Long.class));
  }
}
