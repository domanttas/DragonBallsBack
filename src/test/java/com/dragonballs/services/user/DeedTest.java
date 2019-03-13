package com.dragonballs.services.user;

import com.dragonballs.controllers.DeedController;
import com.dragonballs.entities.Deed;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

public class DeedTest {

    @Mock
    private DeedController deedController;

    @Test
    public void deed_deactivation_check() {
        Deed testDeed = new Deed();
        testDeed.setClosed(false);
        testDeed.setId(123L);
        when(deedController.deactivateDeed(123L)).thenReturn().
    }
}
