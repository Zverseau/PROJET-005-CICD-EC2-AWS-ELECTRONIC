import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Commande } from '../model/commande';
import { CommandeService } from '../service/commande.service';


@Component({
  selector: 'app-commande-details',
  templateUrl: './commande-details.component.html',
  styleUrl: './commande-details.component.css'
})
export class CommandeDetailsComponent implements OnInit {

  commande!: Commande;

  constructor(
    private route: ActivatedRoute,
    private commandeService: CommandeService
  ) {}

  ngOnInit(): void {

    const id = this.route.snapshot.paramMap.get('id');

    if(id){
      this.commandeService.getCommandeById(id).subscribe(data => {
        this.commande = data;
      });
    }

  }

}