package com.pet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    private Long price;

    private Long priceSale;

    private Integer quantity;

    private Integer sale;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "product_color", //Tạo ra một join Table tên là "address_person"
            joinColumns = @JoinColumn(name = "product_id"),  // TRong đó, khóa ngoại chính là address_id trỏ tới class hiện tại (Address)
            inverseJoinColumns = @JoinColumn(name = "color_id") //Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới (Person)
    )
    private List<Color> colorList;

    private String image;

    @Column(name = "product_description")
    private String productDescription;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Long bought;

    private String slug;
}
