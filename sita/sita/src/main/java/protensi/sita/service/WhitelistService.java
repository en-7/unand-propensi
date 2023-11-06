package protensi.sita.service;

import protensi.sita.model.WhitelistModel;
import protensi.sita.repository.WhitelistDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class WhitelistService {
    @Autowired
    WhitelistDb whitelistDb;

    public WhitelistModel findByUsername(String username) {
        Optional<WhitelistModel> whitelist = whitelistDb.findByUsername(username);
        if (whitelist.isEmpty()) {
            return null;
        }
        return whitelist.get();

    }

    public WhitelistModel addwhitelist(String username) {
        WhitelistModel whitelist = new WhitelistModel();
        whitelist.setUsername(username);
        return whitelistDb.save(whitelist);
    }

}
