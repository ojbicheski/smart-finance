/**
 * 
 */
package com.smartfinance.customer.mapper;

import org.modelmapper.ModelMapper;

/**
 * @author Orlei Bicheski
 *
 */
public class Mapper<E, D> extends ModelMapper {

	private final Class<E> kEntity;
	private final Class<D> kDto;
	
	/**
	 * @param kEntity Class entity
	 * @param kDto Class DTO
	 */
	public Mapper(Class<E> kEntity, Class<D> kDto) {
		this.kEntity = kEntity;
		this.kDto = kDto;
	}

	/**
	 * Convert DTO to Entity.
	 * 
	 * @param dto Reference to converted
	 * @return Entity
	 */
	public E toEntity(D dto) {
		return kEntity.cast(this.map(dto, kEntity));
	}

	/**
	 * Convert Entity to DTO.
	 * 
	 * @param entity Reference to converted
	 * @return DTO
	 */
	public D toDto(E entity) {
		return kDto.cast(this.map(entity, kDto));
	}

}
