package com.fiap.tc.infrastructure.gateways.mappers.base;

import com.fiap.tc.infrastructure.gateways.mappers.*;
import org.mapstruct.factory.Mappers;

public class MapperConstants {

    private MapperConstants() {
    }

    public static final CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);

    public static final CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    public static final ProductMapper PRODUCT_MAPPER = Mappers.getMapper(ProductMapper.class);

    public static final ProductImageMapper PRODUCT_IMAGE_MAPPER = Mappers.getMapper(ProductImageMapper.class);
}
