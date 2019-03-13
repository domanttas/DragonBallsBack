package com.dragonballs.services.deed;

import com.dragonballs.dao.DeedDAO;
import com.dragonballs.entities.Category;
import com.dragonballs.entities.Deed;
import com.dragonballs.exceptions.DeedException;
import com.dragonballs.services.deed.DeedService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import sun.invoke.empty.Empty;

import java.util.Optional;

public class DeedServiceTest {
    @Mock
    private DeedDAO deedDAO;

    @Captor
    private ArgumentCaptor<Deed> deedCaptor;

    @InjectMocks
    private DeedService deedService;

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findById_should_return_existing_deed() {

        String expectedName = "fakeName";
        String expectedDescription = "fakeDescription";
        Category expectedCategory = new Category();
        String expectedCategoryName = "fakeCategory";

        Deed fakeDeed = new Deed();
        Long id = fakeDeed.getId();

        fakeDeed.setName(expectedName);
        fakeDeed.setDescription(expectedDescription);
        expectedCategory.setName(expectedCategoryName);
        fakeDeed.setCategory(expectedCategory);

        Mockito.when(deedDAO.findById(fakeDeed.getId())).thenReturn(Optional.of(fakeDeed));

        Deed actualDeed = deedService.findById(id);

        Assert.assertEquals(fakeDeed, actualDeed);
    }

    @Test
    public void findById_should_throw_exception() {

        Long fakeId = 1L;

        Mockito.when(deedDAO.findById(fakeId)).thenReturn(Optional.empty());
        //Act and assert
        thrownException.expect(DeedException.class);

        deedService.findById(fakeId);
    }

}
