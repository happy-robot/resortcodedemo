package kz.kaps.resort.dataproviders.database.images;

import kz.kaps.resort.dataproviders.database.ad.AdEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rsrt_images")
public class ImageEntity {

    public static final String ATTRIBUTE_AD = "ad";

    @Id
    @Column(name = "id_")
    @SequenceGenerator(name= "IMAGES_SEQUENCE", sequenceName = "IMAGES_SEQUENCE_ID",
            initialValue=100, allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.AUTO, generator="IMAGES_SEQUENCE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id_", nullable = false)
    public AdEntity ad;

    @Column(name = "file_name_with_ext_")
    private String fileNameWithExt;

    @Column(name = "mime_type_")
    private String mimeType;

    @Column(name = "order_number_", nullable = false)
    private Integer orderNumber;

}
