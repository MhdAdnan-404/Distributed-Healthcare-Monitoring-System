package com.mhd.accountManagement.services;

import com.mhd.accountManagement.exception.UserDosentExistException;
import com.mhd.accountManagement.model.DTO.DoctorDTO.DoctorMapper;
import com.mhd.accountManagement.model.DTO.UserDTO.UserUpdateRequest;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.repository.DoctorRepository;
import com.mhd.accountManagement.repository.UserRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorService doctorService;

    public List<Users> getAllUsers(){
        List<Users> usersList = userRepository.findAll();
        return usersList;
    }

    public Users updateUser(UserUpdateRequest request){
        var user = userRepository.findById(request.userId())
                .orElseThrow(() -> new UserDosentExistException(format("Cannot update Customer: No customer found with the provided ID: %s", request.userId())
                ));
        mergeUser(user, request);
        return userRepository.save(user);
    }

    public boolean deleteUser(Integer userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDosentExistException("User with ID " + userId + " does not exist."));

        user.setIsDeleted(true);
        userRepository.save(user);
        return true;
    }

    private void mergeUser(Users user, UserUpdateRequest request){
        if(StringUtils.isNotBlank(request.username())){
            user.setUsername(request.username());
        }
        if(StringUtils.isNotBlank(String.valueOf(request.role()))){
            user.setRole(request.role());
        }
    }


}
