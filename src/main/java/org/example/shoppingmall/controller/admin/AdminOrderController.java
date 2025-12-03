package org.example.shoppingmall.controller.admin;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.Service.OrderService;
import org.example.shoppingmall.Service.DeliveryService;
import org.example.shoppingmall.domain.Order;
import org.example.shoppingmall.domain.OrderStatus;
import org.example.shoppingmall.domain.DeliveryState;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;
    private final DeliveryService deliveryService;

    @GetMapping
    public String orderList(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/order-list";
    }

    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.findById(id);

        model.addAttribute("order", order);
        model.addAttribute("delivery", deliveryService.findByOrderId(id));

        model.addAttribute("orderStatuses", OrderStatus.values());
        model.addAttribute("deliveryStates", DeliveryState.values());

        return "admin/order-detail";
    }

    @PostMapping("/{id}/updateStatus")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        orderService.updateStatus(id, status);
        return "redirect:/admin/orders/" + id;
    }

    @PostMapping("/{id}/updateDelivery")
    public String updateDelivery(
            @PathVariable Long id,
            @RequestParam DeliveryState state,
            @RequestParam(required = false) String trackingNo) {

        deliveryService.updateDelivery(id, state, trackingNo);
        return "redirect:/admin/orders/" + id;
    }
}
