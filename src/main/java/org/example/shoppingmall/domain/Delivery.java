package org.example.shoppingmall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private String address;

    @Column(name = "tracking_no")
    private String trackingNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryState state;

    @PrePersist
    protected void onCreate() {
        // 주문 생성 시 자동 초기화
        if (this.trackingNo == null) {
            this.trackingNo = null;  // 또는 "" 가능
        }
        if (this.state == null) {
            this.state = DeliveryState.PREPARING;
        }
    }

    public boolean isDeliveryStarted() {
        return this.state == DeliveryState.SHIPPING || this.state == DeliveryState.DELIVERED;
    }

}
