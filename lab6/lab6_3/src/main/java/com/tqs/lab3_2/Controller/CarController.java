package com.tqs.lab3_2.Controller;

import com.tqs.lab3_2.Model.Car;
import com.tqs.lab3_2.Model.CarDTO;
import com.tqs.lab3_2.Service.CarManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody CarDTO c)
    {
        Car cPers = new Car();
        cPers.setMaker(c.getMaker());
        cPers.setModel(c.getModel());

        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(cPers);
        return new ResponseEntity<>(saved,status);
    }

    @GetMapping(path="/cars")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value="id") Long id) {
        Optional<Car> carOp = carManagerService.getCarDetails(id);
        if(carOp.isEmpty()) {
            throw new NoSuchElementException();
        }
        return ResponseEntity.ok().body(carOp.get());
    }
}
