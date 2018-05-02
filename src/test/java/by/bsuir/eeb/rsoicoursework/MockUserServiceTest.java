package by.bsuir.eeb.rsoicoursework;

import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import by.bsuir.eeb.rsoicoursework.service.impl.UserServiceImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 02.04.2018 / 23:35
 * email: bogdanshishkin1998@gmail.com
 */

public class MockUserServiceTest {

    private List<User> users = new ArrayList<>();

    {
        User user1 = new User();
        user1.setFirstName("Stefania");
        user1.setLastName("Schukina");
        user1.setEmail("sshukina@gmail.com");
        User user2 = new User();
        user2.setFirstName("Bogdan");
        user2.setLastName("Shishkin");
        user2.setEmail("bogdanshishkin1998@gmail.com");
        User user3 = new User();
        user3.setFirstName("Stas");
        user3.setLastName("Shishkin");
        user3.setEmail("netu97@tut.by");
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Test
    public void testGetUserByEmail() {
        UserService userService = mock(UserServiceImpl.class);
        Page page = Page.getInstance(0, 2);

        when(userService.getAllLimited(page))
                .thenReturn(users.subList(0, 2));
        when(userService.findByEmail(users.get(1).getEmail())).thenReturn(users.get(1));
        when(userService.getAll()).thenReturn(users);

        assertEquals(userService.findByEmail("bogdanshishkin1998@gmail.com")
                .getEmail(), "bogdanshishkin1998@gmail.com");
        assertThat(userService.getAllLimited(page).size(), is(2));
        assertThat(userService.getAll().size(), is(3));
    }
}
