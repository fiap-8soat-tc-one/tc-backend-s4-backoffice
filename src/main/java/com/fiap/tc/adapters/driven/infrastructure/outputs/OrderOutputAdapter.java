package com.fiap.tc.adapters.driven.infrastructure.outputs;

import com.fiap.tc.adapters.driven.infrastructure.persistence.repositories.OrderRepository;
import com.fiap.tc.adapters.driven.infrastructure.persistence.builders.OrderHistoricBuilder;
import com.fiap.tc.adapters.driven.infrastructure.persistence.mappers.base.MapperConstants;
import com.fiap.tc.core.domain.exception.NotFoundException;
import com.fiap.tc.core.domain.model.Order;
import com.fiap.tc.core.domain.model.enums.OrderStatus;
import com.fiap.tc.core.domain.response.OrderListResponse;
import com.fiap.tc.core.application.port.out.order.ListOrdersReadyPreparingOutputPort;
import com.fiap.tc.core.application.port.out.order.LoadOrderOutputPort;
import com.fiap.tc.core.application.port.out.order.UpdateStatusOrderOutputPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class OrderOutputAdapter implements UpdateStatusOrderOutputPort, LoadOrderOutputPort, ListOrdersReadyPreparingOutputPort {

    private final OrderRepository orderRepository;

    public OrderOutputAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void update(UUID idOrder, OrderStatus status) {

        var orderEntityOptional = orderRepository.findByUuid(idOrder);

        if (orderEntityOptional.isEmpty()) {
            throw new NotFoundException(format("Order id %s not found!", idOrder));
        }

        var orderEntity = orderEntityOptional.get();
        orderEntity.getStatus().getValidator().validate(status);
        orderEntity.setStatus(status);
        orderEntity.getAudit().setUpdatedDate(LocalDateTime.now());
        orderEntity.getOrderHistoric().add(OrderHistoricBuilder.create(orderEntity, orderEntity.getStatus()));

        orderRepository.save(orderEntity);
    }


    @Override
    public Order load(UUID uuid) {
        var orderEntityOptional = orderRepository.findByUuid(uuid);
        if (orderEntityOptional.isEmpty()) {
            throw new NotFoundException(format("Order id %s not found!", uuid));
        }
        return MapperConstants.ORDER_MAPPER.fromEntity(orderEntityOptional.get());
    }

    @Override
    public Page<OrderListResponse> list(List<OrderStatus> status, Pageable pageable) {
        var ordersEntity = orderRepository.findByStatusIn(status, pageable);
        return ordersEntity.map(MapperConstants.ORDER_LIST_MAPPER::fromEntity);
    }
}
