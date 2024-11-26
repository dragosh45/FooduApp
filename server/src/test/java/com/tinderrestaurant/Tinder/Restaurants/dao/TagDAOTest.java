package com.tinderrestaurant.Tinder.Restaurants.dao;

import com.tinderrestaurant.Tinder.Restaurants.model.Tag;
import com.tinderrestaurant.Tinder.Restaurants.repository.TagRepository;
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
public class TagDAOTest {

  @Mock private TagDAO tagDao;
  @Mock private TagRepository tagRepository;

  @Before
  public void setup() {
    tagRepository = Mockito.mock(TagRepository.class);
    // TODO: fix your tests
    //        tagDao = new TagDAO(tagRepository);
  }

  @Test
  public void newTagTest() {
    Mockito.when(tagDao.save(any(Tag.class))).thenReturn(new Tag());
    Tag tag = new Tag();
    assertThat(tagDao.save(tag)).isNotNull();
  }

  @Test
  public void newTagWithIdTest() {
    Mockito.when(tagDao.save(any(Tag.class)))
        .then(
            new Answer<Tag>() {
              @Override
              public Tag answer(InvocationOnMock invocation) {
                Object[] arguments = invocation.getArguments();
                if (arguments != null && arguments.length > 0 && arguments[0] != null) {
                  Tag tag = (Tag) arguments[0];
                  tag.setId(new Long(1));
                  return tag;
                }
                return null;
              }
            });
    Tag tag = new Tag();
    assertThat(tagDao.save(tag)).isNotNull();
  }

  @Test
  public void deleteTagTest() {
    tagDao.delete(new Long(1));
    verify(tagRepository).deleteById(any(Long.class));
  }
}
