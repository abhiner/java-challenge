package com.reliaquest.api.model;

import lombok.Data;

@Data
public class EmployeeSingleResponse {
    private Employee data;
    private String status;
}
