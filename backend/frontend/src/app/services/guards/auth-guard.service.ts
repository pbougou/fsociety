import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {tokenNotExpired} from 'angular2-jwt';

import {TOKEN_NAME} from '../../constants/auth.constants';
import {UserService} from '../authentication/user.service';

@Injectable()
export class AuthGuardService implements CanActivate {
  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const isParent = this.userService.isParentUser();
    const isAdmin = this.userService.isAdminUser();
    if ((tokenNotExpired(TOKEN_NAME, this.userService.accessToken) && isParent)||isAdmin) {
      return true;
    } else {
      this.router.navigate(['login-page']);
      return false;
    }
  }
}
