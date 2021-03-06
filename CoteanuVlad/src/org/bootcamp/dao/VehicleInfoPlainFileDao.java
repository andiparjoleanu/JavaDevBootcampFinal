package org.bootcamp.dao;

import org.bootcamp.model.VehicleInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public final class VehicleInfoPlainFileDao implements VehicleInfoDao {

    private static final String SEPARATOR = ";";
    private static final int VEHICLE_TYPE = 1;
    private static final int VEHICLE_AGE = 3;
    private static final int VEHICLE_MILES = 4;
    private static final int VEHICLE_IS_DIESEL = 5;
    private static final int VEHICLE_ID = 0;
    private static final int VEHICLE_FORMULA = 2;

    private final VehicleInfo.Builder vehicleInfoBuilder = VehicleInfo.builder();
    private final Map<String, VehicleInfo> vehicleInfoMap = new HashMap<>();

    public VehicleInfoPlainFileDao(String filePath) {
        VehicleInfo vehicleInfo;
        try {
            final File inputFile = new File(filePath);
            final InputStream inputStream = new FileInputStream(inputFile);
            final Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                final String[] tokens = line.split(SEPARATOR);

                vehicleInfo = vehicleInfoBuilder
                        .withId(tokens[VEHICLE_ID])
                        .withVehicleTypeName(tokens[VEHICLE_TYPE])
                        .withVehicleTypeFormula(tokens[VEHICLE_FORMULA])
                        .withAge(Integer.parseInt(tokens[VEHICLE_AGE]))
                        .withNumberOfMiles(Long.parseLong(tokens[VEHICLE_MILES]))
                        .withIsDiesel(Boolean.parseBoolean(tokens[VEHICLE_IS_DIESEL]))
                        .build();

                vehicleInfoMap.put(vehicleInfo.getId(), vehicleInfo);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            final String errorMessage = "Can not create instance of class " + VehicleInfoPlainFileDao.class.getSimpleName();
            throw new IllegalStateException(errorMessage);
        }
    }

    @Override
    public List<VehicleInfo> getAllVehicles() {
        return new ArrayList<>(vehicleInfoMap.values());
    }

    @Override
    public VehicleInfo getVehicleById(String id) {
        return vehicleInfoMap.get(id);
    }
}
