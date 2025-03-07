package com.alibaba.drivermanagement.model.entity;


import com.alibaba.drivermanagement.model.enums.TripNotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip_notifications", schema = "drivers")
@Entity
@Builder
public class TripNotification {
    @Id
    @Column(name = "notification_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "located_at")
    private String locatedAt;
    @Column(name = "drop_at")
    private String dropAt;
    @Enumerated(EnumType.STRING)
    private TripNotificationStatus status;
    private String driver;
    private BigDecimal amount;
    @Column(name = "trip_tran_no")
    private String tripTranNo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
