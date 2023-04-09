package com.clochelabs;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Date;

public class UrneTest {

    @Test
    public void vote_ajouter()
    {
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        Key[] key = Crypto.KeyGen(15);
        BigInteger[] vote = Crypto.Encrypt((PublicKey) key[0],1);
        boolean pass = Urne.getInstance().addVote(vote,0,0);
        Assert.assertTrue(pass);
    }

    @Test
    public void votant_ne_peut_pas_voter_plusieur_fois()
    {
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        Key[] key = Crypto.KeyGen(15);
        BigInteger[] vote = Crypto.Encrypt((PublicKey) key[0],1);
        Urne.getInstance().addVote(vote,0,0);
        boolean pass = Urne.getInstance().addVote(vote,0,0);

        Assert.assertFalse(pass);
    }

    @Test
    public void plusieur_personne_peuvent_voter()
    {
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        Key[] key = Crypto.KeyGen(15);
        BigInteger[] vote = Crypto.Encrypt((PublicKey) key[0],1);
        Urne.getInstance().addVote(vote,0,0);
        boolean pass = Urne.getInstance().addVote(vote,1,0);

        Assert.assertTrue(pass);
    }

    @Test
    public void plusieur_scrutin_existe_plusieur_utilisateur_peuvent_voter_pour_eux()
    {
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        Urne.getInstance().newRef("marine","oui","non",new Date());
        Key[] key = Crypto.KeyGen(15);
        BigInteger[] vote = Crypto.Encrypt((PublicKey) key[0],1);
        Urne.getInstance().addVote(vote,0,0);
        boolean pass = Urne.getInstance().addVote(vote,1,1);
    }

    @Test
    public void cloreScrutin()
    {
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
    }

    @Test
    public void test_new_id_when_add_poll(){
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        assert(Urne.getInstance().newId() == 1);
        System.out.println(Urne.getInstance().getRef().get(0).getId());
        Urne.getInstance().newRef("melenchon","oui","non",new Date());
        assert(Urne.getInstance().newId() == 2);

    }
}
