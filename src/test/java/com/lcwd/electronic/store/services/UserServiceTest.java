package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.repositories.RoleRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    User user;
    Role role;
    String roleId;
    @BeforeEach
    public void init()
    {
        role=Role.builder().roleId("abc").name("NORMAL").build();
       user= User.builder()
                .name("Aryan")
                .email("aryan@gamil.com")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();
       roleId="abc";
    }

    //create user
    @Test
    public void createUserTest()
    {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(roleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(role));
        UserDto user1 = userService.createUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Aryan",user1.getName());

    }

    //update user

    @Test
    public void updateUserTest()
    {
        String userId="user";
        UserDto userDto = UserDto.builder()
                .name("Aryan pandit")
                .about("this is update")
                .gender("male")
                .imageName("abc.png")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updatedUser = userService.updateUser(userDto, userId);
        System.out.println(updatedUser.getName());
        //System.out.println(updatedUser);
        Assertions.assertNotNull(updatedUser);
    }

    //delete user test case
    @Test
    public void deleteUserTest() throws IOException {
        String userId="man";
        Mockito.when(userRepository.findById("man")).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }

    public void getAllUsersTest()
    {
        User user1= User.builder()
                .name("Aryan1")
                .email("aryan@gamil.com1")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();

        User user2= User.builder()
                .name("Aryan2")
                .email("aryan@gamil.com2")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();


        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page=new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<UserDto> allUser=userService.getAllUser(1,2,"name","asc");
        Assertions.assertEquals(3,allUser.getContent().size());
    }

    @Test
    public void getUserByIdTest()
    {
        String userId="qwerty";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserDto user1 = userService.getUserById(userId);
        Assertions.assertNotNull(user1);
        Assertions.assertEquals(user.getName(),user1.getName());
    }

    @Test
    public void getUserByEmailTest()
    {
        String email="abc";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(email);
        Assertions.assertNotNull(userDto);
    }

    @Test
    public void searchUserTest()
    {
        User user1= User.builder()
                .name("Aryan1")
                .email("aryan@gamil.com1")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();

        User user2= User.builder()
                .name("Aryan2")
                .email("aryan@gamil.com2")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();
        User user3= User.builder()
                .name("Rahul3")
                .email("aryan@gamil.com2")
                .about("Testing")
                .gender("male")
                .imageName("abc.png")
                .password("aryan")
                .roles(List.of(role))
                .build();

        String keyword="Aryan";
        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(Arrays.asList(user1,user2,user3));
        List<UserDto> dtoList = userService.searchUser(keyword);
        Assertions.assertEquals(3,dtoList.size());
    }

    @Test
    public void findUserByEmailTest()
    {
        String email="aryan.gmail.com";
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail(email);
        Assertions.assertNotNull(userDto);
    }

}
