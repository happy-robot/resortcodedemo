package kz.kaps.resort.dataproviders.database.pricetag;

import kz.kaps.resort.dataproviders.database.ad.AdEntity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "rsrt_price_tags")
public class PriceTagEntity {

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Long id;

    @Column(name = "price_")
    private Integer price;

    @Column(name = "start_date_")
    private LocalDate startDate;

    @Column(name = "end_date_")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id_", nullable = false)
    private AdEntity ad;

    public PriceTagEntity(){}

    private PriceTagEntity(PriceTagEntity.Builder builder){
        this.id = builder.id;
        this.price = builder.price;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.ad = builder.ad;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public AdEntity getAd() {
        return ad;
    }

    public void setAd(AdEntity ad) {
        this.ad = ad;
    }

    public static class Builder {
        private Long id;
        private Integer price;
        private LocalDate startDate;
        private LocalDate endDate;
        private AdEntity ad;

        public Builder id(Long id){
            this.id = id;
            return this;
        }

        public Builder price(Integer price){
            this.price = price;
            return this;
        }

        public Builder startDate(LocalDate startDate){
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDate endDate){
            this.endDate = endDate;
            return this;
        }

        public Builder ad(AdEntity ad){
            this.ad = ad;
            return this;
        }

        public PriceTagEntity build(){
            return new PriceTagEntity(this);
        }
    }
}
