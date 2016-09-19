package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.AI.alphabeta;
import static comp1110.ass2.StratoGame.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static comp1110.ass2.TestUtility.*;
import java.util.Random;

/**
 * Created by Joseph Meltzer on 13/09/2016.
 */
public class AITestByJoseph {
    // For each sample placement, generate a move with every possible piece and ensure that the move is not an empty string.
    @Test
    public void testNonEmpty() {
        for (char i = 'K'; i<='T'; i++) {
            assertFalse("Placement MMUA with piece " + i + " gave an empty string", generateMove("MMUA", i, 'A') == "");
             for (String placement : greenTestPlacements) {
                if (isPlacementWellFormed(placement+"AA"+i+"A")) {
                    assertFalse("Placement " + placement + " with piece " + i + " gave an empty string", generateMove(placement, i, 'A') == "");
                }
            }
        }
        for (char i = 'A'; i<='J'; i++) {
            for (String placement : redTestPlacements) {
                if (isPlacementWellFormed(placement+"AA"+i+"K")) {
                    assertFalse("Placement " + placement + " with piece " + i + " gave an empty string", generateMove(placement, i, 'A') == "");
                }
            }
        }
    }

    @Test
    public void testValid() {
        for (char i = 'K'; i<='T'; i++) {
            assertTrue("Placement MMUA with piece " + i + " generated an invalid piece", isPlacementValid("MMUA"+generateMove("MMUA", i, 'A')));
            for (String placement : greenTestPlacements) {
                if (isPlacementWellFormed(placement+"AA"+i+"A")) {
                    assertTrue("Placement " + placement + " with piece " + i + " generated an invalid piece", isPlacementValid(placement+generateMove(placement, i, 'A')));
                }
            }
        }
        for (char i = 'A'; i<='J'; i++) {
            for (String placement : redTestPlacements) {
                if (isPlacementWellFormed(placement+"AA"+i+"K")) {
                    assertTrue("Placement " + placement + " with piece " + i + " generated an invalid piece", isPlacementValid(placement+generateMove(placement, i, 'A')));
                }
            }
        }
    }

    // For each sample placement and every possible piece, ensure that the Alphabeta function with depth 1
    // returns the same result as  the original, more naive code.
    @Test
    public void testAlphaBetaAtDepth1() {
        for (char i = 'K'; i<='T'; i++) {
            for (String placement : greenTestPlacements){
                if (isPlacementWellFormed(placement+"AA"+i+"A")) {
                    String ab = alphabeta(placement, i, 'A', 1, -100, 1000, true, true).move;
                    String og = oldGenerator(placement, i, 'A');
                    assertTrue("With placement "+placement+", AB gave "+ab+", old gave "+og, ab.equals(og));
                }
            }
        }
        for (char i = 'A'; i<='J'; i++) {
            for (String placement : redTestPlacements){
                if (isPlacementWellFormed(placement+"AA"+i+"A")) {
                    String ab = alphabeta(placement, i, 'A', 1, -100, 1000, true, false).move;
                    String og = oldGenerator(placement, i, 'A');
                    assertTrue("With placement "+placement+", AB gave "+ab+", old gave "+og, ab.equals(og));
                }
            }
        }
    }

    static char[] checkOrder = {'M','L','N','K','O','J','P','I','Q','H','R','G','S','F','T','E','U','D','V','C','W','B','X','A','Y','Z'};

    public static String oldGenerator(String placement, char piece, char oppPiece) {
        String bestMove = "";
        int bestScore = -100;
        for (char x : checkOrder) {
            for (char y : checkOrder) {
                for (char o = 'A'; o <= 'D'; o++) {
                    if (piece >= 'A' && piece <= 'J') {
                        if (isPlacementValid(placement + x + y + piece + o) && getScoreForPlacement(placement + x + y + piece + o, false)  - getScoreForPlacement(placement + x + y + piece + o, true)> bestScore) {
                            bestMove = "" + x + y + piece + o;
                            bestScore = getScoreForPlacement(placement + x + y + piece + o, false) - getScoreForPlacement(placement + x + y + piece + o, true);
                        }
                    }
                    if (piece >= 'K' && piece <= 'T') {
                        if (isPlacementValid(placement + x + y + piece + o) && getScoreForPlacement(placement + x + y + piece + o, true) - getScoreForPlacement(placement + x + y + piece + o, false) > bestScore) {
                            bestMove = "" + x + y + piece + o;
                            bestScore = getScoreForPlacement(placement + x + y + piece + o, true) - getScoreForPlacement(placement + x + y + piece + o, false);
                        }
                    }
                }
            }
        }
        return bestMove;
    }

    static final String greenTestPlacements[] = {
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAKJLAPPABQKMCJNECRLRBLQGBNPKBLPHDPRPBJOFAMRRDOKHCMINCOTGAQITDTIIBRPKCKIDCRSOBTPCCSRQASGCAQKPBQUADPRLCQNJAIPSBGOIB",
            "MMUAMLODKNJDLPTDKOBBJNOCJMBAOLPALJEDJPNBQLFAHNNCLKHBLRLCNKHDHKKBKSGANKQAMJDBOJQDQOACKVPCPNACOPMDJOIAPLLBHJDCJLMDGIFCFOTDJXCCRPSCHVGAGQRDJVCBSNKBEIECLLRCIZID",
            "MMUANMTDOOADNPRAPLBCNOQCPPDAPNRDQLCDSNPBPPJDPITAMPBBPRLBMQIDTMNBRSICQTLBULFBSKPDROCBRIMDPUHBPHQDPSJDMSNDPLDBMMOALLHDRHKBKPGBNIKAJLFAVLSDQUACWMOBTMEAVOMDSREA",
            "MMUAKMPDKKCDKOKDKQIDMKPAKRGAJNOCNNADHMQDIPJCGQNDKMHAKHKAGMECMHQAJIABOLMDKTEAKWRDMSBCJWSCGOBDILNDPNDDOGLCFOFCNITAKQGALFMARLJBKYRDMYIANROANQDDSMLDGWFDHRTAQRHB",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAOJNBMPGAQNQAINCAIQRDMJABNQOAJJDDJRQASOEBJTRAPPCBPRSAKPIBHILAUPICRMMDLPADUNTDRPFBURLCKIHDPUKDJMJCJQKDVRGDMHPCWPHC",
            "MMUAKMKAMKIAIMNDJKICJNQBNMEDIIQBGJDCFILCEJDBKNOCIGABIMMAHLGCKIOADKFBHONDJGHAEMMBEICALGKAMHAAEGPBCHCACLPBBMHBNOLCBPJDFLSCKFBCCNRAKFEBOIRDMOGDEETBBEFAEOSBEKBB",
            "MMUANMLDNNGAMOTBNQFBKMLDLLJDOLRDOLDBJMPCORADQNSBRLICJKQDPOHBHJKAKQEAGISDJRJCPNOAKPFCNSKBITACHPNDFIECROOAQQBBOSTAGQBAOJMCKTDDGMRDFICDPIPDSJICFLMCTHGAFQQCUIHA",
            "MMUAMKNAMPCCONMBNJDCNQSBLQFBPOPAOREANJRBQNDDPKNCOTBBORKBKKGAKMSAKRCBPTPDQUGBOOKDKPICMTTCQRIAOLRBSUJCLUMBKIAAROQARLJCRJLDIJBDOPQBKWHCHRTDTUHDMHOCGPABJNLBJYFD",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMPSAMLDANKKCQPJCMSQCMJHCRLRCNSEDKPMBTLACNITDKRABTJPCQRCCSILCLHBAKUQDJIGDMFNAPHIBUJPDUHFCSIKBOFHDOKRDXJCBWILCQJGA",
            "MMUAKNQDLLCBJNNCLMJDHMTDNLJCONQCJKHBFLLDEJGAJJMDPLGAMOTALKHDDJOBGIBCAJRAJPBCKHSBDJCAMRNCRKABNOPCHKECLQSCMKFAGLPCNRFALQKBPKDCGPOASMIBJNMAOOAAGRRBKSEDOOLDSPIC",
            "MMUAKNMAIOIDMOKAMLFDJQKDIPBBOMMBPMDAMNLBLJHALPRBMQJAQOPCMLDAHMOAPQECSMOCORGAGMTCTNHCPONDPLCDNJTAKLJCGNPBVMEBPQSBVPGCLMLCRPFDQQQAGQAAQLNBTOABFSSCMIICKMQDLGBD",
            "MMUANLQAPLEAMKTDMOCAPJSBLQCDLLMDKOJBHOMDOPHBRKOBORBBHPKBMSFCPHNANIADQILAPNBBQGPDSHHAJKTAREJCJNODSFIBTEPASKGDNFRALUACHORATDICUHLATLEBNEQDQPDDPKNDIQDDRRKDRCFD",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMKSDNJDDROQCMQGDMIKCNHBDJPPAJRIALGLBMOICPQLDPREAQHTCIPACOFOAOTFCRMRCPJJAMUQAGPHBEPMCGPHAMPKBSNGAKVPAPTAALSRDSQCC",
            "MMUANMMDOLFDLMRBLKDAQLTDPJHANNSARMIASJMCOPBBMQKCJMIBQMQBUMCCVNNBKLHCQPPDIOJDGONDIPGBUNODTHBCWLKBJPJAPQSDKJGDLOTBVQADUPPBQIFCFOLBQIDBTRLAOKEAFQOAKKADSNRACOEA",
            "MMUAMOOBMOACKMTDNNGDOOMBORJCNKNAJNHDOSPBOIHBKOSBJQFANSLDKMCCJSKAMSABPMSBMQIAHOPAQLIBHTRAKUGDLVMBPHDCFOKAPTEAHMTAOUBBMJNAIRECPRQDSRCCPKRDOJJBKWOBNHBCMWLARPFA",
            "MMUAKMRDKOICIMKDNOACOPTDPRJDMPKBPSFALLPDHOGDJQNCOQABMNQDSRDCKKLCKHFBQOMCTSHBHKOALJHDKGLDNICATUQAQUEBPVNBPSBDOOOAPXEBUVMASTCBUXPAGODBMHSBVZBDOHSCSRIBGKRBNFJB",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAONLBNJFBIMNDPQICHMQBQOJDRNPDJNABIPPAJKEDGNQBKQGDHKLBQRHDNRRCTMCCLRKBJHHAMTSCRPAATRMCLGGAURTDUNIBOIKAFMDCLVRDVTBC",
            "MMUAMPOCNPDAPOKARNGBQMMDLRIDSPTDJPADKSRDTNFANNLANRHALRRAQMHAIRTAHRDBUONARLEDQPQAIQIDHQQCSRJCJNMDRJFDHNSBSTBCSVPCMKCASWOBILJBNMKDURACWRSCYRCBHULDUXECWNNCSIBD",
            "MMUANNMAOPEDPONDMLJCNMKDOLBDNQSCKQFAMOTBKSAAQPQAQSBCSRPCKMAAQURCTRHDUSSBPKIDITLDRVDDQINAPVJARWOBOXCDQGMAJOICRYOBIVGCTRPCSSEBSKRBNSGBMNTALUCCMWKAWRDBQMLAVQFC",
            "MMUALLKBOMICQMLCOOGDROSCSPECNOOBRLADRINBSKCDVKKCLOGBRRODSNBDQRQBUOJBMQTCPHHAPKQCUMIAQUMDOSDAMRTARVFBPGPDTVECNUSCJNJAPDRBNLCCQRMAIODBJLNCPVABRYPCRTBDVORDPCFC",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAQNPDILBASMLBQOIASKKBINABQKLCJPCDPIQATOIBQHKDQNCBPKRDROADPGNBMPHAHOMBJKJCKJSDLRHCIQTBKTDDLPQBSJGCEORDSHEDOEPAUIFB",
            "MMUALLPBKNDCJLRCIOGAKJKAKOJALITCMKBDJJNCKRACNHRCKMDALRODJGHBJKKAPHHBNMTDINGCJTSCKSJAGJMDQGECRHLCFHFDPLNARJEDQGQDGOBANQQAFIIBTGSCKVICUGPDEPCANNOALFABRKLBKECD",
            "MMUALORAMLIDOLNALRADNPLCPNJBKSODKUFDPPRAORJDNQTBMRADNPKBOSGAKLNDPJBAJLPBSPHBHKSAPIECMTTDNKFDQTQDJVCCITMAQRHDIWSASOIDLVOBLNCCUPMCHYDDQKQATKECKPPBLRGCSILAHPDD",
            "MMUAOMTBNKDALLNAMJIBKKQCMPACLNPBMHFBNOPDKHCBNLMBOJDDQKKDNPIAJJRCQIBCNNNAPIEBJGLDPOHARIMAPNGDHKODIMJCFMSDKQECTIKAKRBASLSDVLJBJHLDGNCBNSRDIPFBJDOBGKHCLGTDOOGA",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFARMKCQNCAQNQBKQCDPMKDQKHBLJOALQICPIRBOHGDQMRBKRJANRLCJSECLHSBJGIDPPQAJRFAJTPBLFBCJPMCJJGAINTBMDHCQGLCHGDDRLPBMPAA",
            "MMUAKNPAKMADLOSALQJBLKOAMSHDKOMDMPGALTQDKRFBJOLBKTIBMSLAOMICIUTCJNBCKKTCFUJAOQRBNTEAJJKCEVABIKQBFVECLJNDJVDAPQRDJOBCKXSBMMGBEUMCIYFDOUOAGVCAQMPBKOCCHXNCLGDB",
            "MMUALMSCKODDONNBOPGBIMRDLJJBHKKAHICBKPLBNJFCOIODHPFAGMOANGGDOGMBNEEDIKMBMCIAPMQCMOEARMPBPPHDIQTALQCAMETBQQHBINLAKKABSLPCKNJBNNSAKLBDPMKDGQACRSQCPHDDMGNBTLID",
            "MMUAMORANMGDNQLCOOIDKNTDOKAAJOOCPMBDMKNDKLDCLLNDKODANRTAMLCBOLKCOPCAINRCQOJDOQOCLJJCNJSDRQECTQMBLRECTTQDKTBCUUPBMTHDNJSBNVGDISKBKRFAVQMCSOADHLQDRUHDWULCNXFC",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAQMPDROFCJKKAHLJAKKNBLKHDFLQDSMDDUNPDRPGBWNLAUMBBNKTDJOEASOSDMOACFMKAGNIAHORAYNHAJJRCGMABRROBEKGBJQMBLOCDPRLCOTID",
            "MMUAMPTDOOGCOQKCMKEANLQAPREDQLPBNSFCRLPAQKADKLOATLGDQLKCNUHDTNSDPKHCPMRBMUICQSSBRSJDLKMCUSDBUQLAVOBAUJRAWLFBPONAUIIDWSNCKOCDSQODRNABJLQBSTCAKKMAXRJDOILBLWBC",
            "MMUAMPMDLPDCMQPBLMICLKQCORJCPQTAQRIAMTNDKIABLJKBMLHDMRLAJPCBJORDNNGALUSDJMJCMQTCKRFCJPSAMTBARTQDPSEBKTMCIOACPMRAPLECUTNCNVBDGPPAIRFCTUOASSHDUROCUODAEPKAQSCD",
            "MMUAMPNCJPEDKRTCKOBDNOPDOMICNQSDNSDCHRNALLGDNNLBISCAPRLBIRBAKJPBPQADGSOCPNEAIKRAHTJBPMKDIJGCRRQBSNFCGKKCMQJBKTTCROAANPMBHVHBPOMCUOHCIGOBJWFBESRBHYDDUPSAEJCB",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAOONDQLAAJPODHPIAMJQAHOFDGMKAPQECOKSDFQHAGSLDMHDBIRMBQLIBGOLBLQGBEOPCHNCCJTRDNIGDMHRAGKAAGRPBFMBCHTKBJMCBMPQAKRJB",
            "MMUAMKNBMPCCKNPDJODCOLLBHOECOKPDLRACRKMCPIEAIQODPJDCGPOAOPJBSISANRBDGNLCHNFBSLNCKJHDTMSCFLFANQKAQRJCUKTCILHBDMQDGKBCOORDVIGBTOTDOOAAJLQAJICCQORDKTIDPTKDPPIA",
            "MMUANORCONDARNTCNOADTMLCNMFDLLODNKFCQLSAQJGANPQBMQBBPIRAMQHAORQDQRJDLSNAUMDDLVOCONGDJKKDSSABIKNCJHHAQHSCLMIBJUPBRSCDWNKBJFIBJWTAQOBASJMAXOEALWMAOTEDIXPBIVCB",
            "MMUALONDMQJCLRTBLMCCKLLCNPIDKSNBJKDCKROAIRHBPPMDGQHBRQKDQQFBEPTDNQFAPRQBSRCALNRDTTBDQQMDDOABPUQDOUJBMSSAKIGBNRPADRGDDPLDOXBDUSODMPDANNSDWREBKOKCGSEBNKPAUSIC",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMPPANJCBQPODJLHCSPPDRQEBSMRALKADNNTDSRGAMJMAPPIBGLQATTACJNKBTLFCELLAVRGCJPRACMBAMLSANQJAAMQDJIDAUPKDIJICVMNBRLHC",
            "MMUANMNAKMDAJOOCMLHCIMRDGMFDKOKAJLCDQMLCNOBBFKTAHOECJIKBIPJAFOPCMIEADKSAJHDCFQTCNRGCESNDPNAASLRBKJHAHKQDDRACMGMBSNJBKILDMPGBEUMDOPCANGSDGHIAJOPABLBDBIOABSIC",
            "MMUANNLANMBDPLLCKNEAKPOANQHDLMMCIPCALQKAJRABKQTDQKCDQNKBIRJDRLPAJUDCROTDONIDSQOCMNHDIMRDHRDCTLSAIVGBTRNBMSJDQMSCOSGDRRQBOOIAGPPAKWFCHSMBKYFCUMNANOBAHKRAPTAB",
            "MMUANNODMLFCPOLBLKHCPQMBPTEDJJTAQVGCMPLDILEAPMRCRSABOSNCKPCDPQSCRWGBMNPAQMADSSKAJQBCRPQCSXHAHRTAJHFBGMNAPSDDPXQCJNIAIPSCPKIDFNOBFLBAOVRCTQCAEMKCPYJANTMBFPDB",
    };

    static final String redTestPlacements[] = {
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAKJLAPPABQKMCJNECRLRBLQGBNPKBLPHDPRPBJOFAMRRDOKHCMINCOTGAQITDTIIBRPKCKIDCRSOBTPCCSRQASGCAQKPBQUADPRLCQNJAIPSB",
            "MMUAMLODKNJDLPTDKOBBJNOCJMBAOLPALJEDJPNBQLFAHNNCLKHBLRLCNKHDHKKBKSGANKQAMJDBOJQDQOACKVPCPNACOPMDJOIAPLLBHJDCJLMDGIFCFOTDJXCCRPSCHVGAGQRDJVCBSNKBEIECLLRC",
            "MMUANMTDOOADNPRAPLBCNOQCPPDAPNRDQLCDSNPBPPJDPITAMPBBPRLBMQIDTMNBRSICQTLBULFBSKPDROCBRIMDPUHBPHQDPSJDMSNDPLDBMMOALLHDRHKBKPGBNIKAJLFAVLSDQUACWMOBTMEAVOMD",
            "MMUAKMPDKKCDKOKDKQIDMKPAKRGAJNOCNNADHMQDIPJCGQNDKMHAKHKAGMECMHQAJIABOLMDKTEAKWRDMSBCJWSCGOBDILNDPNDDOGLCFOFCNITAKQGALFMARLJBKYRDMYIANROANQDDSMLDGWFDHRTA",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAOJNBMPGAQNQAINCAIQRDMJABNQOAJJDDJRQASOEBJTRAPPCBPRSAKPIBHILAUPICRMMDLPADUNTDRPFBURLCKIHDPUKDJMJCJQKDVRGDMHPC",
            "MMUAKMKAMKIAIMNDJKICJNQBNMEDIIQBGJDCFILCEJDBKNOCIGABIMMAHLGCKIOADKFBHONDJGHAEMMBEICALGKAMHAAEGPBCHCACLPBBMHBNOLCBPJDFLSCKFBCCNRAKFEBOIRDMOGDEETBBEFAEOSB",
            "MMUANMLDNNGAMOTBNQFBKMLDLLJDOLRDOLDBJMPCORADQNSBRLICJKQDPOHBHJKAKQEAGISDJRJCPNOAKPFCNSKBITACHPNDFIECROOAQQBBOSTAGQBAOJMCKTDDGMRDFICDPIPDSJICFLMCTHGAFQQC",
            "MMUAMKNAMPCCONMBNJDCNQSBLQFBPOPAOREANJRBQNDDPKNCOTBBORKBKKGAKMSAKRCBPTPDQUGBOOKDKPICMTTCQRIAOLRBSUJCLUMBKIAAROQARLJCRJLDIJBDOPQBKWHCHRTDTUHDMHOCGPABJNLB",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMPSAMLDANKKCQPJCMSQCMJHCRLRCNSEDKPMBTLACNITDKRABTJPCQRCCSILCLHBAKUQDJIGDMFNAPHIBUJPDUHFCSIKBOFHDOKRDXJCBWILC",
            "MMUAKNQDLLCBJNNCLMJDHMTDNLJCONQCJKHBFLLDEJGAJJMDPLGAMOTALKHDDJOBGIBCAJRAJPBCKHSBDJCAMRNCRKABNOPCHKECLQSCMKFAGLPCNRFALQKBPKDCGPOASMIBJNMAOOAAGRRBKSEDOOLD",
            "MMUAKNMAIOIDMOKAMLFDJQKDIPBBOMMBPMDAMNLBLJHALPRBMQJAQOPCMLDAHMOAPQECSMOCORGAGMTCTNHCPONDPLCDNJTAKLJCGNPBVMEBPQSBVPGCLMLCRPFDQQQAGQAAQLNBTOABFSSCMIICKMQD",
            "MMUANLQAPLEAMKTDMOCAPJSBLQCDLLMDKOJBHOMDOPHBRKOBORBBHPKBMSFCPHNANIADQILAPNBBQGPDSHHAJKTAREJCJNODSFIBTEPASKGDNFRALUACHORATDICUHLATLEBNEQDQPDDPKNDIQDDRRKD",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMKSDNJDDROQCMQGDMIKCNHBDJPPAJRIALGLBMOICPQLDPREAQHTCIPACOFOAOTFCRMRCPJJAMUQAGPHBEPMCGPHAMPKBSNGAKVPAPTAALSRD",
            "MMUANMMDOLFDLMRBLKDAQLTDPJHANNSARMIASJMCOPBBMQKCJMIBQMQBUMCCVNNBKLHCQPPDIOJDGONDIPGBUNODTHBCWLKBJPJAPQSDKJGDLOTBVQADUPPBQIFCFOLBQIDBTRLAOKEAFQOAKKADSNRA",
            "MMUAMOOBMOACKMTDNNGDOOMBORJCNKNAJNHDOSPBOIHBKOSBJQFANSLDKMCCJSKAMSABPMSBMQIAHOPAQLIBHTRAKUGDLVMBPHDCFOKAPTEAHMTAOUBBMJNAIRECPRQDSRCCPKRDOJJBKWOBNHBCMWLA",
            "MMUAKMRDKOICIMKDNOACOPTDPRJDMPKBPSFALLPDHOGDJQNCOQABMNQDSRDCKKLCKHFBQOMCTSHBHKOALJHDKGLDNICATUQAQUEBPVNBPSBDOOOAPXEBUVMASTCBUXPAGODBMHSBVZBDOHSCSRIBGKRB",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAONLBNJFBIMNDPQICHMQBQOJDRNPDJNABIPPAJKEDGNQBKQGDHKLBQRHDNRRCTMCCLRKBJHHAMTSCRPAATRMCLGGAURTDUNIBOIKAFMDCLVRD",
            "MMUAMPOCNPDAPOKARNGBQMMDLRIDSPTDJPADKSRDTNFANNLANRHALRRAQMHAIRTAHRDBUONARLEDQPQAIQIDHQQCSRJCJNMDRJFDHNSBSTBCSVPCMKCASWOBILJBNMKDURACWRSCYRCBHULDUXECWNNC",
            "MMUANNMAOPEDPONDMLJCNMKDOLBDNQSCKQFAMOTBKSAAQPQAQSBCSRPCKMAAQURCTRHDUSSBPKIDITLDRVDDQINAPVJARWOBOXCDQGMAJOICRYOBIVGCTRPCSSEBSKRBNSGBMNTALUCCMWKAWRDBQMLA",
            "MMUALLKBOMICQMLCOOGDROSCSPECNOOBRLADRINBSKCDVKKCLOGBRRODSNBDQRQBUOJBMQTCPHHAPKQCUMIAQUMDOSDAMRTARVFBPGPDTVECNUSCJNJAPDRBNLCCQRMAIODBJLNCPVABRYPCRTBDVORD",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAQNPDILBASMLBQOIASKKBINABQKLCJPCDPIQATOIBQHKDQNCBPKRDROADPGNBMPHAHOMBJKJCKJSDLRHCIQTBKTDDLPQBSJGCEORDSHEDOEPA",
            "MMUALLPBKNDCJLRCIOGAKJKAKOJALITCMKBDJJNCKRACNHRCKMDALRODJGHBJKKAPHHBNMTDINGCJTSCKSJAGJMDQGECRHLCFHFDPLNARJEDQGQDGOBANQQAFIIBTGSCKVICUGPDEPCANNOALFABRKLB",
            "MMUALORAMLIDOLNALRADNPLCPNJBKSODKUFDPPRAORJDNQTBMRADNPKBOSGAKLNDPJBAJLPBSPHBHKSAPIECMTTDNKFDQTQDJVCCITMAQRHDIWSASOIDLVOBLNCCUPMCHYDDQKQATKECKPPBLRGCSILA",
            "MMUAOMTBNKDALLNAMJIBKKQCMPACLNPBMHFBNOPDKHCBNLMBOJDDQKKDNPIAJJRCQIBCNNNAPIEBJGLDPOHARIMAPNGDHKODIMJCFMSDKQECTIKAKRBASLSDVLJBJHLDGNCBNSRDIPFBJDOBGKHCLGTD",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFARMKCQNCAQNQBKQCDPMKDQKHBLJOALQICPIRBOHGDQMRBKRJANRLCJSECLHSBJGIDPPQAJRFAJTPBLFBCJPMCJJGAINTBMDHCQGLCHGDDRLPB",
            "MMUAKNPAKMADLOSALQJBLKOAMSHDKOMDMPGALTQDKRFBJOLBKTIBMSLAOMICIUTCJNBCKKTCFUJAOQRBNTEAJJKCEVABIKQBFVECLJNDJVDAPQRDJOBCKXSBMMGBEUMCIYFDOUOAGVCAQMPBKOCCHXNC",
            "MMUALMSCKODDONNBOPGBIMRDLJJBHKKAHICBKPLBNJFCOIODHPFAGMOANGGDOGMBNEEDIKMBMCIAPMQCMOEARMPBPPHDIQTALQCAMETBQQHBINLAKKABSLPCKNJBNNSAKLBDPMKDGQACRSQCPHDDMGNB",
            "MMUAMORANMGDNQLCOOIDKNTDOKAAJOOCPMBDMKNDKLDCLLNDKODANRTAMLCBOLKCOPCAINRCQOJDOQOCLJJCNJSDRQECTQMBLRECTTQDKTBCUUPBMTHDNJSBNVGDISKBKRFAVQMCSOADHLQDRUHDWULC",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAQMPDROFCJKKAHLJAKKNBLKHDFLQDSMDDUNPDRPGBWNLAUMBBNKTDJOEASOSDMOACFMKAGNIAHORAYNHAJJRCGMABRROBEKGBJQMBLOCDPRLC",
            "MMUAMPTDOOGCOQKCMKEANLQAPREDQLPBNSFCRLPAQKADKLOATLGDQLKCNUHDTNSDPKHCPMRBMUICQSSBRSJDLKMCUSDBUQLAVOBAUJRAWLFBPONAUIIDWSNCKOCDSQODRNABJLQBSTCAKKMAXRJDOILB",
            "MMUAMPMDLPDCMQPBLMICLKQCORJCPQTAQRIAMTNDKIABLJKBMLHDMRLAJPCBJORDNNGALUSDJMJCMQTCKRFCJPSAMTBARTQDPSEBKTMCIOACPMRAPLECUTNCNVBDGPPAIRFCTUOASSHDUROCUODAEPKA",
            "MMUAMPNCJPEDKRTCKOBDNOPDOMICNQSDNSDCHRNALLGDNNLBISCAPRLBIRBAKJPBPQADGSOCPNEAIKRAHTJBPMKDIJGCRRQBSNFCGKKCMQJBKTTCROAANPMBHVHBPOMCUOHCIGOBJWFBESRBHYDDUPSA",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAOONDQLAAJPODHPIAMJQAHOFDGMKAPQECOKSDFQHAGSLDMHDBIRMBQLIBGOLBLQGBEOPCHNCCJTRDNIGDMHRAGKAAGRPBFMBCHTKBJMCBMPQA",
            "MMUAMKNBMPCCKNPDJODCOLLBHOECOKPDLRACRKMCPIEAIQODPJDCGPOAOPJBSISANRBDGNLCHNFBSLNCKJHDTMSCFLFANQKAQRJCUKTCILHBDMQDGKBCOORDVIGBTOTDOOAAJLQAJICCQORDKTIDPTKD",
            "MMUANORCONDARNTCNOADTMLCNMFDLLODNKFCQLSAQJGANPQBMQBBPIRAMQHAORQDQRJDLSNAUMDDLVOCONGDJKKDSSABIKNCJHHAQHSCLMIBJUPBRSCDWNKBJFIBJWTAQOBASJMAXOEALWMAOTEDIXPB",
            "MMUALONDMQJCLRTBLMCCKLLCNPIDKSNBJKDCKROAIRHBPPMDGQHBRQKDQQFBEPTDNQFAPRQBSRCALNRDTTBDQQMDDOABPUQDOUJBMSSAKIGBNRPADRGDDPLDOXBDUSODMPDANNSDWREBKOKCGSEBNKPA",
            "MMUANLOBLNBCONSCKLDAPOTCMLEBPLMBKNJDOLNBLOFAMPPANJCBQPODJLHCSPPDRQEBSMRALKADNNTDSRGAMJMAPPIBGLQATTACJNKBTLFCELLAVRGCJPRACMBAMLSANQJAAMQDJIDAUPKDIJICVMNB",
            "MMUANMNAKMDAJOOCMLHCIMRDGMFDKOKAJLCDQMLCNOBBFKTAHOECJIKBIPJAFOPCMIEADKSAJHDCFQTCNRGCESNDPNAASLRBKJHAHKQDDRACMGMBSNJBKILDMPGBEUMDOPCANGSDGHIAJOPABLBDBIOA",
            "MMUANNLANMBDPLLCKNEAKPOANQHDLMMCIPCALQKAJRABKQTDQKCDQNKBIRJDRLPAJUDCROTDONIDSQOCMNHDIMRDHRDCTLSAIVGBTRNBMSJDQMSCOSGDRRQBOOIAGPPAKWFCHSMBKYFCUMNANOBAHKRA",
            "MMUANNODMLFCPOLBLKHCPQMBPTEDJJTAQVGCMPLDILEAPMRCRSABOSNCKPCDPQSCRWGBMNPAQMADSSKAJQBCRPQCSXHAHRTAJHFBGMNAPSDDPXQCJNIAIPSCPKIDFNOBFLBAOVRCTQCAEMKCPYJANTMB",
    };

}
