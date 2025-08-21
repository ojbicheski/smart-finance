/**
 * 
 */
package com.smartfinance.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

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
	private Mapper(Class<E> kEntity, Class<D> kDto) {
		this.kEntity = kEntity;
		this.kDto = kDto;
	}

	/**
	 * Convert DTO to Entity.
	 * 
	 * @param dto Reference to converted
	 * @return Entity
	 */
	public E entity(D dto) {
		return kEntity.cast(this.map(dto, kEntity));
	}

	/**
	 * Convert Entity to DTO.
	 * 
	 * @param entity Reference to converted
	 * @return DTO
	 */
	public D dto(E entity) {
		return kDto.cast(this.map(entity, kDto));
	}

	public static <E, D> Builder<E, D> builder(Class<E> entity, Class<D> dto) {
		return new Builder<E, D>(entity, dto);
	}

	public static class Builder<E, D> {
		private Class<E> entity;
		private Class<D> dto;

		private PropertyMap<D, E> mapToEntity;
		private PropertyMap<E, D> mapToDto;

		public Builder(Class<E> entity, Class<D> dto) {
			this.entity = entity;
			this.dto = dto;
		}

		public Builder<E, D> mappingEntity(PropertyMap<D, E> mapToEntity) {
			this.mapToEntity = mapToEntity;
			return this;
		}

		public Builder<E, D> mappingDto(PropertyMap<E, D> mapToDto) {
			this.mapToDto = mapToDto;
			return this;
		}

		public Mapper<E, D> entityMapper() {
			Mapper<E, D> mapper = new Mapper<>(entity, dto);
			if (mapToEntity != null) {
				mapper.addMappings(mapToEntity);
			}

			return mapper;
		}

		public Mapper<E, D> dtoMapper() {
			Mapper<E, D> mapper = new Mapper<>(entity, dto);
			if (mapToDto != null) {
				mapper.addMappings(mapToDto);
			}

			return mapper;
		}
	}
}