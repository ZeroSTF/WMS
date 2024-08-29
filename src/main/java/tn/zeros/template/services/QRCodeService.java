package tn.zeros.template.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.zeros.template.entities.Bon;
import tn.zeros.template.entities.Transaction;

import java.io.ByteArrayOutputStream;

@RequiredArgsConstructor
@Service
@Slf4j
public class QRCodeService {

    public byte[] generateQRCodeImage(Bon bon) throws Exception {
        String bonInfo = createBonQRCodeText(bon);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(bonInfo, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

    private String createBonQRCodeText(Bon bon) {
        StringBuilder sb = new StringBuilder();
        sb.append("Bon ID: ").append(bon.getId()).append("\n");
        sb.append("Type: ").append(bon.getType()).append("\n");
        sb.append("Date: ").append(bon.getDate()).append("\n");
        sb.append("Sender: ").append(bon.getSender().getUsername()).append("\n");
        sb.append("Receiver: ").append(bon.getReceiver().getUsername()).append("\n");
        sb.append("Transactions:\n");

        for (Transaction transaction : bon.getTransactions()) {
            sb.append("- Produit: ").append(transaction.getProduits().getRefArt())
                    .append(", Quantité: ").append(transaction.getQuantity())
                    .append(", Montant: ").append(transaction.getMontant())
                    .append("\n");
        }

        return sb.toString();
    }
}


/*public byte[] generateQRCodeImage(Bon bon) throws Exception {
        String bonInfo = String.valueOf(bon.getId());

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(bonInfo, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }*/
