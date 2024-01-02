package com.enigma.warungmakanbahari.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_table")
public class TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String tableName;
}
