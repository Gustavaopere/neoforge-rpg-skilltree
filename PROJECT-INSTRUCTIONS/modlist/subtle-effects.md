# Subtle Effects

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81c4aafff045e5581e70
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `SubtleEffects-neoforge-1.21.1-1.14.3.jar`, mod id `subtle_effects`, runtime `1.14.3`; Fzzy Config 0.7.6 presente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física atual de 11/09/2026”. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Subtle Effects 1.14.3 e Fzzy Config 0.7.6 estão presentes. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Subtle Effects
- **Arquivo JAR:** `SubtleEffects-neoforge-1.21.1-1.14.3.jar`
- **Versão 1.21.1:** 1.14.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Camada ambiental de partículas e alguns sons: sparks, smoke/dust, sleep/slime/fluff/magic feedback, low-health/hunger cues, mob-head shaders e outros detalhes, com culling/render distance próprios.
- **Dependências:** Fzzy Config é obrigatória e está presente fisicamente como `fzzy_config-0.7.6+1.21+neoforge.jar`. O mod é principalmente client-side, mas algumas features requerem server access; quando instalado no servidor, a FAQ atual exige o mod nos clientes.
- **Sobreposição:** Cruza com Sounds e outros mods de partículas/atmosfera, embora cada um cubra conjuntos diferentes de efeitos.
- **Compatibilidade/Riscos:** Riscos: particle stacking, double culling com PartiCull, shader interaction, client/server deployment e config drift. O upstream afirma compatibilidade com Particular, mas o custo final do stack deve ser medido localmente. Não atribuir efeitos cosméticos a gameplay authority.
- **Observações:** mod id `subtle_effects`; runtime 1.14.3. Decisão Sem decisão preservada. Config física não foi lida. A lista pública é parcial; não foi inventada contagem de particles/sounds nem lista de features server-assisted sem tabela version-pinned.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial Subtle Effects 1.14.3 File ID 8008312 + Fzzy Config 0.7.6 físico. Dossiê de 08/09 preservado; config e runtime visual não foram testados.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/subtle-effects ; https://www.curseforge.com/minecraft/mc-mods/subtle-effects/files/8008312 ; https://modrinth.com/mod/subtle-effects
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — Subtle Effects 1.14.3 permanece exatamente instalado; particle/sound features, culling, Fzzy Config, client/server boundary, integrações, lifecycle, performance, riscos e testes preservados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `SubtleEffects-neoforge-1.21.1-1.14.3.jar`, mod id `subtle_effects`, versão `1.14.3`. Subtle Effects adiciona **feedback ambiental e de entidades por partículas e alguns sons**, com culling/render distance próprios e configuração granular.

## 1. Identidade, versão e decisão
- **Mod:** Subtle Effects.
- **JAR físico:** `SubtleEffects-neoforge-1.21.1-1.14.3.jar`.
- **Mod id:** `subtle_effects`.
- **Versão:** `1.14.3`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Release:** build oficial exata de 29/04/2026.
- **Ambiente:** projetado principalmente client-side, com features adicionais que podem exigir server access.
- **Decisão:** Sem decisão; preservada.

## 2. Dependência
O projeto requer **Fzzy Config**. O pack contém `fzzy_config-0.7.6+1.21+neoforge.jar`.

Fzzy Config é authority da infraestrutura de config; Subtle Effects define as opções e o comportamento visual/sonoro correspondente.

## 3. Authority e ownership
Subtle Effects controla:
- spawn/render de suas partículas;
- alguns sons ambientais/feedback;
- culling e render distance internos das suas próprias partículas;
- opções de ativação/customização das features.

Ele **não** deve virar authority do evento lógico que inspira o efeito. Exemplo: heartbeat visual/sonoro em vida baixa não altera health; burning sparks não alteram fire damage; dust cloud de landing não muda física.

## 4. Features visuais/sonoras publicadas
A página atual destaca:
- sparks em fire, campfires, candles, torches e blocos relacionados;
- villagers e players dormindo com Z particles e snore;
- slime trails;
- dust particles em glowstone/redstone;
- magic particles em Allay/Vex;
- fluff ao tosquiar sheep;
- burning entities com sparks/smoke/flames;
- textura de vanilla smoke retrabalhada;
- heartbeat com jogador em **3 hearts ou menos**;
- stomach growl em **3 hunger ou menos**;
- spectator shader ao usar certos mob heads, incluindo Creeper e Enderman head de Supplementaries conforme suporte publicado;
- running dust clouds para jogadores e alguns mobs;
- landing dust clouds para mobs.

A documentação diz “e muito mais”; esta ficha não converte isso em inventário exaustivo sem o registry/source version-pinned completo.

## 5. Particle culling e render distance
O mod inclui **particle culling próprio** e **particle render distance** configurável.

Isso é importante porque o pack também usa PartiCull:
- Subtle Effects controla culling/alcance das suas superfícies conforme config própria;
- PartiCull pode aplicar redução dinâmica adicional baseada em FPS;
- ausência visual precisa ser diagnosticada separando provider, culling interno e culling externo.

## 6. Configuração
A FAQ oficial afirma que **todas as features podem ser desativadas** no menu de config e algumas têm customização adicional.

Exemplos documentados:
- Dust Clouds em `Entities/Dust Clouds`;
- dropped-item particles em `Items/Item Rarity`, com `Particle Display Type = OFF` para desativar.

A linha 1.14.2 introduziu mais configs condicionais no menu; o runtime 1.14.3 é posterior. A configuração física do usuário não foi lida, portanto nenhum efeito é afirmado como enabled/disabled localmente.

## 7. Client-only vs server-assisted
O autor descreve o mod como **principalmente client-side**, mas algumas features/detalhes exigem acesso ao servidor.

Regra operacional publicada:
- usado apenas no cliente, as features puramente locais funcionam sem mudar o servidor;
- se o mod for instalado no servidor para habilitar features server-assisted, **todos os clientes precisam ter o mod para entrar** conforme FAQ atual.

Esta ficha não enumera quais features individuais exigem servidor sem a tabela version-pinned da wiki; fail-closed.

## 8. Networking e multiplayer
Quando server-assisted features estão em uso:
- servidor envia somente state/eventos necessários;
- cliente cria feedback visual/sonoro;
- nenhum packet deve transformar efeito cosmético em autoridade de gameplay.

Em multiplayer, clients podem ter configs visuais distintas apenas onde o protocolo permite; regras de conexão precisam ser testadas no deployment real do pack.

## 9. Lifecycle
Validar:
- client boot com config;
- entrar/sair de servidor com e sem Subtle Effects em ambiente de teste;
- resource reload;
- toggles de feature durante sessão quando suportados;
- morte/respawn;
- dimension change;
- many entities simultaneously burning/running/landing;
- low-health/low-hunger threshold crossing;
- sleep/wake villagers/players;
- reload/restart persistindo config.

## 10. Integrações concretas no pack
- **Fzzy Config 0.7.6:** dependency física.
- **PartiCull 2.0:** culling externo adicional; risco de over-culling.
- **Particle Effects 1.5.0:** outro provider de partículas, focado em status effects; domains diferentes, custo somado.
- **Particle Rain / Particular:** aumentam densidade ambiental; o upstream diz compatibilidade com Particular, mas performance final do stack precisa de benchmark local.
- **Iris:** shader pipeline pode alterar blending/depth das partículas.
- **Supplementaries:** Enderman head shader é integração explicitamente citada pelo upstream.
- **Stylish Effects:** HUD de status effects; não é redundante com partículas/sons do Subtle Effects.

## 11. Performance
O custo é majoritariamente client-side em partículas/som/render, mas server-assisted features podem adicionar tráfego/event handling.

O built-in culling e render distance são controles de custo, não garantia de FPS. Com vários particle mods ativos, medir frame time e particle count em cenas representativas.

## 12. Riscos técnicos
1. **Particle stacking:** vários providers podem criar excesso visual/overdraw.
2. **Double culling:** culling interno + PartiCull pode esconder feedback demais.
3. **Client/server requirement:** instalação server-side muda requisito de clientes segundo FAQ; deployment precisa ser uniforme.
4. **Threshold spam:** health/hunger cruzando limite repetidamente não deve disparar som/efeito excessivo.
5. **Config drift:** toggles/conditional configs podem resetar ou mudar entre versões.
6. **Shader interaction:** smoke/sparks/dust podem ficar invisíveis ou exagerados.
7. **Mob/mod compatibility:** efeitos que inspecionam entidades/itens modded podem encontrar edge cases; 1.14.3 inclui correções de crashes com mods externos, mostrando essa superfície de integração.

## 13. Matriz de testes
- [ ] Cliente inicia com Subtle Effects 1.14.3 + Fzzy Config 0.7.6.
- [ ] Teste client-only contra servidor sem mod em ambiente controlado.
- [ ] Teste servidor com mod + cliente com mod.
- [ ] Confirmar política de conexão para cliente sem mod quando servidor possui Subtle Effects.
- [ ] Fire/campfire/candle sparks e burning-entity effects.
- [ ] Sleep Z/snore e slime trail.
- [ ] Heartbeat <=3 hearts e stomach growl <=3 hunger sem spam.
- [ ] Running/landing dust clouds.
- [ ] Culling/render-distance próprio em valores baixo/alto.
- [ ] PartiCull ativo sob FPS baixo sem desaparecer feedback crítico.
- [ ] Iris/shader ativo com smoke/sparks/dust.
- [ ] Stress com Particle Effects/Particle Rain/Particular simultâneos.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 14. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão e Fzzy Config presente.
- CurseForge oficial: build exata `SubtleEffects-neoforge-1.21.1-1.14.3.jar`, release NeoForge 1.21.1.
- Modrinth/README oficiais: feature list, built-in culling/render distance, Fzzy Config, toggles e regra client-side/server-assisted.

## 15. Revalidação física — 11/09/2026
O snapshot atual continua contendo `SubtleEffects-neoforge-1.21.1-1.14.3.jar`, mod id `subtle_effects`, runtime `1.14.3`, com Fzzy Config `0.7.6` presente. A release oficial 1.21.1 permanece exatamente esta build.

A configuração local, deployment client-only/server-assisted e interação real com PartiCull/Iris/Particle Effects não foram executados nesta recatalogação. A matriz permanece integralmente desmarcada.
