package com.tcs.usermicroservice.service;

import com.tcs.usermicroservice.dto.UserDto;
import com.tcs.usermicroservice.entity.User;
import com.tcs.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.orm.ObjectOptimisticLockingFailureException;





@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; 

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDto);
    }

    public UserDto createUser(UserDto userDto) {
    	try {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            User savedUser = userRepository.save(user);
            return convertToDto(savedUser);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new RuntimeException("Conflict detected. Please try again.", e);
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertToDto(User user) {
        UserDto userDTO = new UserDto();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }
}
