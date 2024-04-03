package com.pet.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    private MapperUtils() { };

    public static final ModelMapper MODEL_MAPPER = new ModelMapper();

    //Chuyển đổi một đối tượng Entity sang một DTO tương ứng
    public static <T, R> R entityToDTO(T entity, Class<R> dtoClass) {
        return MODEL_MAPPER.map(entity, dtoClass);
    }

    //Chuyển đổi một DTO sang một Entity tương ứng
    public static <T, R> R dtoToEntity(T dto, Class<R> entityClass) {
        return MODEL_MAPPER.map(dto, entityClass);
    }

    /*
        - Nhận danh sách Entity và một class đại diện cho DTO
        - Sử dụng ModelMapper để ánh xạ từng entity trong danh sách sang đối tượng DTO tương ứng
        - Thu thập kết quả ánh xạ thành một danh sách DTO và trả về.
    */
    public static <T, R> List<R> entitiesToDTOs(List<T> entities, Class<R> dtoClass) {
        return entities
                .stream()
                .map(entity -> MODEL_MAPPER.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /*
        - Nhận danh sách DTO và một class đại diện cho Entity
        - Sử dụng ModelMapper để ánh xạ từng DTO trong danh sách sang đối tượng Entity tương ứng
        - Thu thập kết quả ánh xạ thành một danh sách Entity và trả về.
    */
    public static <T, R> List<R> dtoToEntities(List<T> dtosRequest, Class<R> entityClass) {
        return dtosRequest
                .stream()
                .map(dto -> MODEL_MAPPER.map(dto, entityClass))
                .collect(Collectors.toList());
    }
}
