package com.example.datatablesdemo.model;

import java.util.List;

import lombok.Data;

@Data
public class Select2Model {
    /**
     * InnerSelect2Model
     */
    

    private List<?> results;
    private More pagination;
}

