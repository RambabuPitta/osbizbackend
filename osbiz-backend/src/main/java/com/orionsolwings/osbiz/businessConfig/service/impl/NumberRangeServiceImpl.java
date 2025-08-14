package com.orionsolwings.osbiz.businessConfig.service.impl;

import com.orionsolwings.osbiz.businessConfig.model.NumberRange;
import com.orionsolwings.osbiz.businessConfig.repository.NumberRangeRepository;
import com.orionsolwings.osbiz.businessConfig.service.NumberRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NumberRangeServiceImpl implements NumberRangeService {

    @Autowired
    private NumberRangeRepository repository;

//    @Override
//    public NumberRange create(NumberRange numberRange) {
//        return repository.save(numberRange);
//    }

    @Override
    public Optional<NumberRange> getByModuleType(String moduleType) {
        return repository.findByModuleType(moduleType);
    }

    @Override
    public List<NumberRange> getAll() {
        return repository.findAll();
    }

    @Override
    public NumberRange update(NumberRange numberRange) {
        Optional<NumberRange> existing = repository.findByModuleType(numberRange.getModuleType());
        if (existing.isPresent()) {
            NumberRange entity = existing.get();
            entity.setBusinessCode(numberRange.getBusinessCode());
            entity.setCurrentNumber(numberRange.getCurrentNumber());
            entity.setMinNumber(numberRange.getMinNumber());
            entity.setMaxNumber(numberRange.getMaxNumber());
            return repository.save(entity);
        }
        throw new RuntimeException("NumberRange not found for moduleType: " + numberRange.getModuleType());
    }

    @Override
    public void deleteByModuleType(String moduleType) {
        repository.deleteByModuleType(moduleType);
    }

	@Override
	public List<NumberRange> create(List<NumberRange> numberRange) {
		// TODO Auto-generated method stub
		return  repository.saveAll(numberRange);
		
	}
    
    
    
}
