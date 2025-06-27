package com.mhd.accountManagement.controller;

import com.mhd.accountManagement.model.Enums.ContactType;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;

import java.util.Map;


public abstract class AbstractController<
        RegisterRequest,
        RegisterResponse,
        UpdateRequest,
        UpdateResponse,
        Entity,
        ID> {

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(registerEntity(request));
    }

    @PostMapping("/update")
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateRequest request) {
        updateEntity(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<Entity>> getAll(Pageable pageable){
        return ResponseEntity.ok(getAllEntities(pageable));

    }

    @GetMapping("/getContactsForEntitiy/{id}")
    public ResponseEntity<Map<ContactType,String>> getContactsForEntity(@PathVariable @Min(value = 1, message = "User ID must be greater than 0") Integer id){
        return ResponseEntity.ok(getContactInfoForEntity(id));

    }


    protected abstract RegisterResponse registerEntity(RegisterRequest request);
    protected abstract UpdateResponse updateEntity(UpdateRequest request);

    protected abstract Map<ContactType,String> getContactInfoForEntity(Integer id);

    protected abstract Page<Entity> getAllEntities(Pageable pageable);
}