package es.caib.dp3t.ibcovid.configbackend.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_sdk_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Config implements Serializable {
    private static final long serialVersionUID = 7204143231081831337L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ConfigType name;

    @Basic
    @Column(name = "value", nullable = false)
    private String value;

    @Basic
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
