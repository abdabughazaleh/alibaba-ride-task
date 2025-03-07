package com.alibaba.ride.model.entity;

import com.alibaba.ride.model.enums.RideStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ride", schema = "ride_management")
public class Ride {
    @Id
    @Column(name = "ride_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    @Column(name = "mobile_no")
    private String mobileNo;
    @Column(name = "located_at")
    private String locatedAt;
    @Column(name = "drop_at")
    private String dropAt;
    @Enumerated(EnumType.STRING)
    private RideStatus status;
    private String driver;
    private BigDecimal amount;
    @Column(name = "driver_mobile_no")
    private String driverMobileNo;
    @Column(name = "transaction_no", unique = true, nullable = false, updatable = false)
    private String transactionNo;

    @PrePersist
    public void setTransactionNo() {
        if (this.transactionNo == null) {
            this.transactionNo = UUID.randomUUID().toString();
        }
    }

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
