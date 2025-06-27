package com.mhd.accountManagement.controller;

import com.mhd.accountManagement.model.DTO.DoctorDTO.DoctorRegisterResponse;
import com.mhd.accountManagement.model.DTO.UserDTO.UserUpdateRequest;
import com.mhd.accountManagement.model.DTO.UserDTO.UserMapper;
import com.mhd.accountManagement.model.DTO.UserDTO.UserResponse;
import com.mhd.accountManagement.model.DTO.UserDTO.UserUpdateResponse;
import com.mhd.accountManagement.model.Users;
import com.mhd.accountManagement.services.AdminService;
import com.mhd.accountManagement.services.DoctorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserMapper mapper;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> users = adminService.getAllUsers()
                .stream()
                .map(mapper::toUserResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PutMapping("/approveDoctor/{doctorId}")
    public ResponseEntity<String> approveDoctor(@PathVariable @Min(value = 1, message = "Doctor ID must be greater than 0") Integer doctorId){
        return ResponseEntity.ok(doctorService.approveDoctor(doctorId));
    }


    @GetMapping("/getAllPending")
    public ResponseEntity<List<DoctorRegisterResponse>> getPendingDoctors(){
        List<DoctorRegisterResponse> pendingDoctors = doctorService.getDoctorsPendingApproval();
        return ResponseEntity.ok(pendingDoctors);
    }

    @PostMapping("/update")
    public ResponseEntity<UserUpdateResponse> UpdateCustomer(@RequestBody @Valid UserUpdateRequest request){
            Users updatedUser = adminService.updateUser(request);
            UserUpdateResponse response = mapper.toUserUpdateResponse(updatedUser);
            return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> DeleteUser(@PathVariable @Min(value = 1, message = "User ID must be greater than 0") Integer userId){
        adminService.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }


}
