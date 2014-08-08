/*
 * Copyright 2014 Open mHealth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openmhealth.dsu.controller;


import org.openmhealth.dsu.domain.UserRegistrationData;
import org.openmhealth.dsu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * A controller that manages user accounts.
 *
 * @author Emerson Farrugia
 */
@ApiController
public class UserController {

    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;


    /**
     * Registers a new user.
     *
     * @param registrationData the registration data of the user
     * @return a response entity with status OK if the user is registered, BAD_REQUEST if the request is invalid,
     * or CONFLICT if the user exists
     */
    @RequestMapping(value = "/users", method = POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationData registrationData) {

        if (registrationData == null) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        Set<ConstraintViolation<UserRegistrationData>> constraintViolations = validator.validate(registrationData);

        if (!constraintViolations.isEmpty()) {
            return new ResponseEntity<>(asErrorMessageList(constraintViolations), BAD_REQUEST);
        }

        if (userService.doesUserExist(registrationData.getUsername())) {
            return new ResponseEntity<>(CONFLICT);
        }

        userService.registerUser(registrationData);

        return new ResponseEntity<>(OK);
    }

    protected List<String> asErrorMessageList(Set<ConstraintViolation<UserRegistrationData>> constraintViolations) {

        return constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }
}
