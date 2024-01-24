package paf.batch3.paf.assesment.batch3pafassesment.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import paf.batch3.paf.assesment.batch3pafassesment.model.Accomodation;
import paf.batch3.paf.assesment.batch3pafassesment.model.Listing;
import paf.batch3.paf.assesment.batch3pafassesment.model.Occupancy;
import paf.batch3.paf.assesment.batch3pafassesment.model.Reservation;
import paf.batch3.paf.assesment.batch3pafassesment.repo.ListingRepo;
import paf.batch3.paf.assesment.batch3pafassesment.repo.ReservationRepo;

@Service
public class BnbService {

    @Autowired
    ListingRepo listingRepo;

    @Autowired
    ReservationRepo reservationRepo;

    public List<String> getListOfCountry() {
        List<String> country = new ArrayList<>();
        List<Document> list = listingRepo.getListOfCountry();
        for (Document d : list) {
            String oneCountry = d.getString("_id");
            country.add(oneCountry);
        }

        return country;
    }

    public List<Listing> getQueryDetails(Accomodation acc) {

        List<Document> doc = listingRepo.querySearch(acc.getCountry(), acc.getRangeMin(), acc.getRangeMax(),
                acc.getAccomodates());

        List<Listing> allListings = new ArrayList<>();

        for (Document d : doc) {
            String name = d.getString("name");
            Double price = (d.getDouble("price"));
            String id = d.getString("_id");
            Document iDocument = d.get("images", Document.class);
            String image = iDocument.getString("picture_url");
            Listing listing = new Listing(name, price, image, id);
            allListings.add(listing);
        }

        return allListings;
    }

    public Listing getAccomDetails(String id) {
        // return listingRepo.getAccomDetails(id);
        Document doc = listingRepo.getAccomDetails(id);

        String accomId = doc.getString("_id");
        String name = doc.getString("name");
        String description = doc.getString("description");
        Double price = (doc.getDouble("price"));
        Document iDocument = doc.get("images", Document.class);
        String image = iDocument.getString("picture_url");
        Document addDoc = doc.get("address", Document.class);
        String street = addDoc.getString("street");
        String suburb = addDoc.getString("suburb");
        String country = addDoc.getString("country");
        List<String> amenities = doc.getList("amenities", String.class);

        Listing listing = new Listing(name, price, image, accomId, description, street, suburb, country, amenities);

        return listing;
    }

    public String generateResId(Reservation res) {
        String hexAlphabet = RandomStringUtils.randomAlphabetic(4);
        String hexNum = RandomStringUtils.randomNumeric(4);
        StringBuilder sb = new StringBuilder();
        String resId = sb.append(hexAlphabet).append(hexNum).toString();
        return resId;
    }

    public boolean getVacancy(Reservation res, String id) {
        Occupancy occ = reservationRepo.getVacancy(id);
        Integer dbOcc = occ.getVacancy();
        Integer daysOfStay = res.getDaysOfStay();

        if (dbOcc >= daysOfStay) {
            return true;
        }
        return false;
    }

    @Transactional (rollbackFor = TransactionException.class)
    public boolean performUpdates(Reservation res) throws TransactionException {

        if (!reservationRepo.updateVacancy(res)) {
            throw new TransactionException("cannot update vacancy");
        }

        if (!reservationRepo.insertRes(res)) {
            throw new TransactionException("cannot update booking");
        }

        return false;
    }

}
