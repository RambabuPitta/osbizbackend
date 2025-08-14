package com.orionsolwings.osbiz.businessConfig.service;

import com.orionsolwings.osbiz.businessConfig.model.NumberRange;
import java.util.List;
import java.util.Optional;

public interface NumberRangeService {
    List<NumberRange> create(List<NumberRange> numberRange);
    Optional<NumberRange> getByModuleType(String moduleType);
    List<NumberRange> getAll();
    NumberRange update(NumberRange numberRange);
    void deleteByModuleType(String moduleType);
}
