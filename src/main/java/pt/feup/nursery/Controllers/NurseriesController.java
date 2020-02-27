package pt.feup.nursery.Controllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pt.feup.nursery.Nurseries.NurseryDaoService;
import pt.feup.nursery.authentication.Auth;

import javax.validation.Valid;


@RestController
public class NurseriesController {
    private static final Logger logger = LogManager.getLogger(NurseriesController.class);


    @Autowired
    private NurseryDaoService nurseryDaoService = new NurseryDaoService();


    @GetMapping("/nurseries")
    public ResponseEntity<Object> getListNurseries() {
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getNurseries().getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of Nurseries", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nurseries/{id}")
    public ResponseEntity<Object> getNurseryByID(@PathVariable String id){
        if (id == null || id.isEmpty()) {
            return new ResponseEntity("invalid id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getNursery(id).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get nursery with that id", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/nurseries/types")
    public ResponseEntity<Object> getTypesOfNursery(){
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getNurseryTypes().getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of types of nursery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   /* @GetMapping("/nurseries/{id}/schedules")
    public ResponseEntity<Object> getNurcerySchedule(@PathVariable final String id){
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getNurserySchedule(id).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get nursery schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    @GetMapping("/nurseries/{id}/medicines")
    public ResponseEntity<Object> getMedicinesByNurseryID(@PathVariable final String id){
        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid nursery id", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getMedicines(id).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get medicines from nursery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/nurseries/{id}")
    public ResponseEntity<Object> deleteNurseryByID(@PathVariable final String id){
        if (id == null || id.isEmpty()) {
            return new ResponseEntity("invalid id for deletion", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.deleteNursery(id).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get nursery with that id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/patients/{pid}")
    public ResponseEntity<Object> dischargePatient(@PathVariable String pid){
        if (!StringUtils.hasText(pid)) {
            return new ResponseEntity("invalid pid for deletion", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.dischargePatient(pid).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to discharge patient with that id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/nurseries/{id}/medicines")
    public ResponseEntity<Object> dischargePatient(@PathVariable String id, final @Valid @RequestBody String medicineInfo){
        if (!StringUtils.hasText(id) || !StringUtils.hasText(medicineInfo)) {
            return new ResponseEntity("invalid request info", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.removeMedicineFromNurseryStorage(id, medicineInfo));
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to remove a certain medicine from stock", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nurseries")
    public ResponseEntity<Object> createNursery(final @Valid @RequestBody String nurseryInfo){
        logger.debug("Request to create nursery: {}", nurseryInfo);

        if (!StringUtils.hasText(nurseryInfo)) {
            return new ResponseEntity("invalid request for nursery creation", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.createNursery(nurseryInfo).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to create nursery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nurseries/{id}/medicines")
    public ResponseEntity<Object> requestMedicine(final @PathVariable String id, final @Valid @RequestBody String medicineRequest){
        logger.debug("call to request a medicine from the pharmacy: {}{}",id,  medicineRequest);

        if (!StringUtils.hasText(medicineRequest) || !StringUtils.hasText(id)) {
            return new ResponseEntity("invalid call to request a medicine from the pharmacy", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.requestMedine(id, medicineRequest).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to request a medicine from the pharmacy", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/medicines/notifications")
    public ResponseEntity<Object> requestMedicine(final @Valid @RequestBody String medicineRequest){
        logger.debug("Notify that a medicine is now available: {}", medicineRequest);

        if (!StringUtils.hasText(medicineRequest)) {
            return new ResponseEntity("invalid call to notify that a medicine is now available.", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.notifyMedicineAvailable(medicineRequest).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to process the request that a medicine is now available", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/nurseries/{id}/nurses")
    public ResponseEntity<Object> getNurses(final @PathVariable String id){

        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid call to request nurses", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getNurses(id).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get list of nurses of nursery"+ id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/nurseries/{id}/nurses")
    public ResponseEntity<Object> addNurses(final @PathVariable String id, final @RequestBody String body){

        if (!StringUtils.hasText(id)) {
            return new ResponseEntity("invalid call to request nurses", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.addNurses(id,body).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to add nurse to nursery"+ id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/nurses/availablenurses")
    public ResponseEntity<Object> getAvailableNurses(){
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getAvailableNurses().getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to get available nurses", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/nurseries/{id}")
    public ResponseEntity<Object> updateNursery(final @PathVariable String id, final @Valid @RequestBody String nurseryInfo ) {
        logger.debug("Request to update nursery: {}:{}", id, nurseryInfo);

        if (!StringUtils.hasText(nurseryInfo) || !StringUtils.hasText(id)) {
            return new ResponseEntity("invalid request for nursery update", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.updateNursery(id, nurseryInfo).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to update nursery", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/patients")
    public ResponseEntity<Object> allocatePatients(final @Valid @RequestBody String patientInfo ) {
        logger.debug("Request to allocate patient: {}", patientInfo);

        if (!StringUtils.hasText(patientInfo)) {
            return new ResponseEntity("invalid request for allocate patient", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.allocatePatientToNursery(patientInfo).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to allocate patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@PutMapping("/nurseries/{id}/schedules")
    public ResponseEntity<Object> insertScheduleInNursery(final @Valid @RequestBody String id, final @Valid @RequestBody String scheduleInfo ) {
        logger.debug("Request to allocate schedule to nursery: {},{}",id, scheduleInfo);

        if (!StringUtils.hasText(scheduleInfo) || !StringUtils.hasText(id)) {
            return new ResponseEntity("invalid request for schelude insertion", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.allocateScheduleToNursery(id, scheduleInfo).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to allocate schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

    @PutMapping("/patients/treatments/{pid}")
    public ResponseEntity<Object> updatePatientTreatments(final @Valid @RequestBody String pid, final @Valid @RequestBody String treatmentsInfo ) {
        logger.debug("Request to update treatments of patient: {},{}", pid, treatmentsInfo);

        if (!StringUtils.hasText(treatmentsInfo) || !StringUtils.hasText(pid)) {
            return new ResponseEntity("invalid request for update treatments of patient", HttpStatus.BAD_REQUEST);
        }
        try {
            return ResponseEntity.ok().body(nurseryDaoService.updatePatientTreatments(pid, treatmentsInfo).getValue());
        }
        catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("failed to update treatments of patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/auth/{id}")
    public String getToken(@PathVariable String id){
        return Auth.createJWT(id,86400000);// 60sec*60min*24hours*1000milisec
    }
    @GetMapping("/auth/login")
    public ResponseEntity<Object> getJwtTokec(){
        try {
            return ResponseEntity.ok().body(nurseryDaoService.getJwtToken().getValue());
        } catch (Exception e) {
            logger.error(e);
            return new ResponseEntity("Error occured in authentication process", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
