package me.droreo002.chestshopconfirmation.database;

import lombok.Getter;
import lombok.Setter;
import me.droreo002.chestshopconfirmation.ChestShopConfirmation;
import me.droreo002.oreocore.database.object.DatabaseFlatFile;
import me.droreo002.oreocore.utils.world.LocationUtils;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class PlayerData {

    @Getter
    private final UUID playerUuid;
    @Getter
    private final DatabaseFlatFile.Data data;
    @Getter @Setter
    private boolean confirmationDisabled;
    @Getter @Setter
    private List<Location> disabledShops;

    public PlayerData(UUID playerUuid, DatabaseFlatFile.Data data) {
        this.playerUuid = playerUuid;
        this.data = data;
        this.confirmationDisabled = data.getConfig().getBoolean("Data.confirmationDisabled");
        this.disabledShops = LocationUtils.toLocations(data.getConfig().getStringList("Data.disabledShops"));
    }

    public void update() {
        final PlayerDatabase playerDatabase = ChestShopConfirmation.getInstance().getPlayerDatabase();

        data.getConfig().set("Data.confirmationDisabled", this.confirmationDisabled);
        final List<String> toSet = LocationUtils.convertToStrings(disabledShops);
        data.getConfig().set("Data.disabledShops", toSet);

        playerDatabase.saveData(data);
    }
}
