package com.hao.tmusicmanagement.util;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;

public class LogTypeConverter implements Converter<Integer> {

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new WriteCellData<Integer>(value.equals(0)?"客户端":"管理员端");
    }
}
