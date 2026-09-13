# Dynamic RPG Resource Bars

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3ca69db9f0db818a85d7e3e8a8e39d57  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Dynamic RPG Resource Bars
- **Arquivo JAR:** `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`
- **Versão 1.21.1:** `0.7.1`
- **Categoria:** RPG; QoL; Visual
- **Decisão:** sem decisão registrada
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/dynamic-rpg-resource-bars
- **Função:** Camada de HUD RPG client-side que substitui/acompanha a apresentação de health, stamina e mana por barras animadas/configuráveis, sem assumir autoridade sobre os recursos fornecidos por Minecraft ou outros mods.
- **Dependências:** NeoForge 1.21.1. Integrações confirmadas pela linha 0.7.x com Ars Nouveau, Iron's Spells 'n Spellbooks, AppleSkin e Farmer's Delight; esses quatro providers estão fisicamente presentes no pack. Suporte a Stamina Attributes existe upstream, mas presença desse provider não foi confirmada na modlist atual.
- **Compatibilidade/Riscos:** Conflitos são principalmente de HUD/overlay: dupla renderização com outros resource bars, posição/escala sobreposta, provider de stamina não presente ou não reconhecido, mount-health substituindo a barra esperada, overlays AppleSkin/Farmer's Delight e configuração antiga após update. O mod não deve liquidar mana/stamina/health.
- **Sobreposição:** Sobrepõe visualmente outros HUD/resource bars, mas não substitui os sistemas que calculam health, mana, stamina, saturation ou buffs. A autoridade permanece no provider de cada recurso.
- **Observações:** Runtime 0.7.1. Ars Nouveau 5.13.1, Iron's Spells 'n Spellbooks 3.16.3, AppleSkin 3.0.9 e Farmer's Delight 1.3.4 estão presentes. O suporte upstream a Stamina Attributes não é prova de que esse provider esteja instalado.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar`, mod id `dynamic_resource_bars` e versão 0.7.1. Escopo e mudanças 0.3.0–0.7.1: publicação/changelogs oficiais do Dynamic RPG Resource Bars.
- **Histórico da decisão:** sem histórico adicional registrado.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — HUD/resources, editor, integrações Ars/ISS/Farmer's Delight/AppleSkin, mount health, lifecycle client, riscos e matriz de testes catalogados.
- **Data da última decisão:** 2026-08-28.

## Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `dynamic_resource_bars-neoforge-0.7.1-1.21.1.jar` · mod id `dynamic_resource_bars` · versão `0.7.1` · NeoForge 1.21.1.

## 1. Papel no modpack
Dynamic RPG Resource Bars é uma camada de **HUD RPG**. Ele apresenta health, stamina e mana em barras animadas e configuráveis, com editor visual, sprites substituíveis e integrações com providers externos.

O mod não é a fonte de verdade desses recursos. Vida continua sob Minecraft; mana permanece sob o mod de magia correspondente; stamina permanece sob o provider que a implementa. A barra apenas lê e apresenta o estado.

## 2. Authority / ownership
- **Minecraft:** health, absorption, armor, air e demais estados vanilla.
- **Ars Nouveau / Iron's Spells 'n Spellbooks:** mana quando a integração correspondente é usada.
- **Provider de stamina:** valor, consumo e regeneração de stamina quando instalado.
- **Dynamic RPG Resource Bars:** layout, animação, cor, sprites, fade, texto e escolha de quando/como exibir cada recurso.

Nenhuma integração própria deve debitar ou restaurar recurso por observar a HUD.

## 3. Linha 0.7.1
A build física é 0.7.1. O changelog dessa versão ajusta a cor padrão da stamina bar. A linha imediatamente anterior, 0.7.0, adiciona suporte a Stamina Attributes, efeito de fade/chunking na parte drenada do recurso e corrige crash envolvendo comportamento vanilla da stamina bar junto a Farmer's Delight.

Esses pontos tornam configuração de stamina e composição com overlays alimentares regressões concretas.

## 4. Mana
A linha 0.6.0 adiciona suporte de mana para:
- Ars Nouveau em NeoForge 1.21.1;
- Iron's Spells 'n Spellbooks em NeoForge 1.21.1.

Ambos estão fisicamente presentes no pack atual. O HUD deve ler o provider ativo sem criar um valor paralelo de mana e sem somar duas fontes quando mais de um sistema mágico estiver carregado.

## 5. AppleSkin e Farmer's Delight
A linha 0.3.0 adicionou integração com AppleSkin e Farmer's Delight para overlays como saturation e efeitos alimentares; 0.6.0 corrigiu uma dependência acidental de AppleSkin.

No pack atual, AppleSkin 3.0.9 e Farmer's Delight 1.3.4 estão presentes. A integração é visual: o estado de food/saturation/effects continua pertencendo aos providers originais.

## 6. Stamina
O projeto suporta Stamina Attributes na linha 0.7.x. **A presença desse provider não foi confirmada na modlist física atual**, portanto esta ficha não afirma que a barra de stamina esteja recebendo dados desse mod no runtime do pack.

Se outro provider de stamina for utilizado, compatibilidade deve ser comprovada por adapter explícito ou teste; sem isso, não presumir que a barra reconheça o recurso.

## 7. Mount health
A linha 0.4.0 introduziu comportamento em que a health bar da montaria pode ocupar a área normalmente usada pela stamina bar durante a montaria. Isso é apresentação condicional, não transferência de authority: a vida da montaria continua no Entity state do servidor.

Regression gate: montar/desmontar, trocar de montaria, receber dano e relogar sem barra residual ou recurso oculto indevidamente.

## 8. Editor e layout
A linha 0.5.0 expandiu fortemente o editor:
- camadas de texto arrastáveis;
- icons/armor/air reposicionáveis;
- cor e tamanho de texto;
- opacidade máxima;
- air bar animada;
- correções de reset/configuração.

O editor deve ser tratado como configuração client-side. Alterar layout não pode modificar valores gameplay.

## 9. Sprites e resource packs
As barras usam sprites customizáveis e podem ser reestilizadas por resource pack. Isso cria uma superfície de compatibilidade com packs visuais: dimensões, anchors e transparência precisam permanecer coerentes com a configuração da HUD.

Resource reload deve atualizar a apresentação sem zerar ou recalcular recursos.

## 10. Client / Server
A função central é client-facing. O cliente lê estados sincronizados e desenha a HUD; o servidor/provider continua decidindo health, mana, stamina, food e effects.

A presença do JAR em ambos os lados, quando exigida pela distribuição/modpack, não torna a HUD authority gameplay.

## 11. Lifecycle
Validar:
- login/relogin;
- respawn;
- dimension change;
- resource reload;
- config reset;
- alteração de GUI scale/resolução;
- montar/desmontar;
- troca de provider de mana;
- aplicação/remoção de efeitos alimentares;
- zero/max resource e regeneration.

## 12. Multiplayer
Cada cliente deve renderizar os próprios recursos a partir do state autorizado. Um cliente não deve conseguir alterar mana/stamina/health por editar HUD/config. Em servidor dedicado, divergência visual client-side não pode criar divergência no resource state real.

## 13. Sobreposição no pack
O domínio de sobreposição é HUD, não gameplay. Outros mods podem desenhar barras, armor/air, saturation ou buffs na mesma região da tela. A solução deve ser escolher precedence/visibilidade por configuração, não remover um provider de gameplay por causa de colisão visual.

## 14. Riscos
1. dupla barra para o mesmo recurso;
2. layout sobrepor chat/hotbar/quest UI;
3. GUI scale produzir clipping;
4. mana de provider incorreto;
5. stamina sem adapter válido;
6. mount health ocultar recurso errado;
7. AppleSkin/Farmer's Delight sobrepor overlays;
8. config antiga produzir posição/opacidade inválida;
9. resource pack trocar sprites sem dimensões compatíveis;
10. confundir valor visual interpolado com estado real.

## 15. Matriz de testes
1. Boot com Ars Nouveau, Iron's Spells, AppleSkin e Farmer's Delight atuais.
2. Health: dano, heal, absorption e death/respawn.
3. Mana Ars Nouveau: consumir/regenerar e relogar.
4. Mana Iron's Spells: consumir/regenerar e relogar.
5. Verificar qual provider de mana a HUD escolhe quando ambos existem.
6. Food/saturation e efeitos Farmer's Delight.
7. Montar/desmontar e validar mount health.
8. GUI scale/resolução mínima e máxima usadas pelo pack.
9. Editar layout, salvar, reiniciar e resetar.
10. Resource reload com sprites customizados.
11. Multiplayer: dois clientes com layouts diferentes e mesmo resource state server-side.

**Esta catalogação não afirma que esses testes foram executados.**

## 16. Evidências
- modlist física canônica de 08/09/2026: JAR, mod id, versão 0.7.1 e providers instalados;
- publicação/changelogs oficiais da linha 0.3.0–0.7.1: AppleSkin/Farmer's Delight, mana Ars/ISS, editor, mount health, stamina e correções.

> **Boundary canônico:** Dynamic RPG Resource Bars é authority da **apresentação**. O recurso real pertence ao provider gameplay correspondente.
