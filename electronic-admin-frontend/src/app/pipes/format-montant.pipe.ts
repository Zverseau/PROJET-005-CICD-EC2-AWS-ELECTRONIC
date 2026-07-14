import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatMontant'
})
export class FormatMontantPipe implements PipeTransform {

  transform(value: number): string {

    if (!value) return '0 FCFA';

    if (value >= 1_000_000_000) {
      return (value / 1_000_000_000).toFixed(3) + 'B';
    }

    if (value >= 1_000_000) {
      return (value / 1_000_000).toFixed(3) + 'M';
    }

    if (value >= 1_000) {
      return (value / 1_000).toFixed(0) + 'K';
    }

    return value + ' FCFA';
  }

}