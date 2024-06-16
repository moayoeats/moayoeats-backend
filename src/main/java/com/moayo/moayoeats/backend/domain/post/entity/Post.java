package com.moayo.moayoeats.backend.domain.post.entity;

import com.moayo.moayoeats.backend.domain.menu.entity.Menu;
import com.moayo.moayoeats.backend.domain.offer.entity.Offer;
import com.moayo.moayoeats.backend.global.entity.BaseTime;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_post")
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Point location;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private Integer minPrice;

    @Column(nullable = false)
    private Boolean amountIsSatisfied;

    @Column(nullable = false)
    private Integer deliveryCost;

    @Column
    private String cuisine;

    @Column
    private Long sumPrice;

    @Column
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Offer> offers;

    @Column
    @Enumerated(EnumType.STRING)
    private PostStatusEnum postStatus;

    @Builder
    public Post(
            String store,
            Integer minPrice,
            Integer deliveryCost,
            LocalDateTime deadline,
            PostStatusEnum postStatus,
            String cuisine,
            Double latitude,
            Double longitude
    ) {
        this.store = store;
        this.minPrice = minPrice;
        this.amountIsSatisfied = false;
        this.deliveryCost = deliveryCost;
        this.deadline = deadline;
        this.postStatus = postStatus;
        this.cuisine = cuisine;
        this.location = setLocation(longitude, latitude);
    }

    private Point setLocation(double longitude, double latitude){
        GeometryFactory geomFactory = new GeometryFactory(new PrecisionModel(), 4326);//4326: Point용 SRID
        return geomFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public void closeApplication() {
        this.postStatus = PostStatusEnum.CLOSED;
    }

    public void completeOrder() {
        this.postStatus = PostStatusEnum.ORDERED;
    }

    public void allReceived(){
        this.postStatus = PostStatusEnum.RECEIVED;
    }

    public void changeAmountGoalStatus() {
        this.amountIsSatisfied = !amountIsSatisfied;
    }
}
