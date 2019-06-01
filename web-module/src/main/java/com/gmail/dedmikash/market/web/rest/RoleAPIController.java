package com.gmail.dedmikash.market.web.rest;

import com.gmail.dedmikash.market.service.RoleService;
import com.gmail.dedmikash.market.service.model.RoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleAPIController {
    private static final Logger logger = LoggerFactory.getLogger(RoleAPIController.class);
    private final RoleService roleService;

    public RoleAPIController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @SuppressWarnings(value = "unchecked")
    public ResponseEntity showRoles() {
        List<RoleDTO> roleDTOList = roleService.getRoles();
        logger.info("All roles were shown with REST API");
        return new ResponseEntity(roleDTOList, HttpStatus.OK);
    }
}
