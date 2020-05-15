package org.multiple.job.common;

public enum MappingType {
	CITY_MAPPING, AREA_MAPPING, SOCIETY_MAPPING, TOWER_MAPPING, FLOOR_MAPPING;

	public static String getName(MappingType type) {
		String name = "NONE";
		switch (type) {
		case CITY_MAPPING:
			name = MappingType.CITY_MAPPING.name();
			break;
		case AREA_MAPPING:
			name = MappingType.AREA_MAPPING.name();
			break;
		case SOCIETY_MAPPING:
			name = MappingType.SOCIETY_MAPPING.name();
			break;
		case TOWER_MAPPING:
			name = MappingType.TOWER_MAPPING.name();
			break;
		case FLOOR_MAPPING:
			name = MappingType.FLOOR_MAPPING.name();
			break;
		default:
			break;
		}
		return name;
	}
}
